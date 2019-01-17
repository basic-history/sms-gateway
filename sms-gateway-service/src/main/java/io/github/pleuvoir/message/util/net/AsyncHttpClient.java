package io.github.pleuvoir.message.util.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步http请求客户端
 * @author abeir
 *
 */
public class AsyncHttpClient implements Closeable{
	
	private static Logger logger = LoggerFactory.getLogger(AsyncHttpClient.class);
	
	//标识不重设超时时间
	private static final int NOOP_RESET_TIMEOUT = -0xFF;
	
	// 最大连接数
	private int maxTotal = 1000;
	// 默认的最大每个路由线程数
	private int defaultMaxPerRoute = 60;
	// 从连接管理器中获取连接的超时时间
	private int connectionRequestTimeout = 5000;
	// 连接超时时间
	private int connectTimeout = 10000;
	// 读数据超时时间
	private int socketTimeout = 30000;
	
	private boolean expectContinueEnabled = true;
	//设置每个路由的最大连接数
	private Map<HttpRoute,Integer> maxPerRoute = new ConcurrentHashMap<>();
	
	private CloseableHttpAsyncClient httpclient = null;
	
	private RequestConfig requestConfig;
	
	/**
	 * 设置连接池的默认的路由线程数
	 * @param max 默认的路由线程数
	 * @return
	 */
	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}
	/**
	 * 设置连接池的每个路由的最大线程数
	 * @param routes 每个路由的最大线程数，key为目标地址uri，value为连接数
	 * @return
	 */
	public void setMaxPerRoute(Map<String,Integer> routes) {
		if(maxPerRoute==null || maxPerRoute.isEmpty()) {
			logger.warn("设置的单个路由的最大线程数失败，参数为空");
			return;
		}
		for(Map.Entry<String,Integer> entity : routes.entrySet()) {
			Integer val = entity.getValue();
			if(val==null || val<0) {
				logger.warn("单个路由的最大线程数不能小于1，当前设置，路由：{}，连接数：{}", entity.getKey(), String.valueOf(val));
				throw new IllegalArgumentException("单个路由的最大线程数不能小于1，当前设置，路由：" + entity.getKey() + "，连接数：" + String.valueOf(val)) ;
			}
			URI uri = URI.create(entity.getKey());
			this.maxPerRoute.put(new HttpRoute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme())), val);
		}
	}
	
	/**
	 * 设置连接池的最大总线程数
	 * @param max 最大总线程数
	 */
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	/**
	 * 设置单个请求从连接管理器中获取连接的超时时间
	 * @param connectionRequestTimeout 从连接管理器中获取连接的超时时间（毫秒）
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	/**
	 * 设置单个请求建立连接的超时时间
	 * @param connectTimeout 建立连接的超时时间（毫秒）
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 设置单个请求的SO_TIMEOUT，数据传输处理超时时间
	 * @param socketTimeout SO_TIMEOUT，数据传输处理超时时间（毫秒）
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setExpectContinueEnabled(boolean expectContinueEnabled) {
		this.expectContinueEnabled = expectContinueEnabled;
	}
	
	
	public AsyncHttpClient() {
	}
	
	/**
	 * 初始化<br>
	 * <ol>
	 * 	<li>信任所有证书</li>
	 *  <li>注册http和https</li>
	 *  <li>配置io线程  </li>
	 *  <li>创建连接池</li>
	 *  <li>创建默认请求配置</li>
	 *  <li>创建同步请求客户端</li>
	 *  <li>启动</li>
	 * </ol>
	 */
	public void init() {
		
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error("设置信任所有证书发生异常，初始化失败", e);
			return;
		}
		// 设置协议http和https对应的处理socket链接工厂的对象  
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder  
                .<SchemeIOSessionStrategy> create()  
                .register("http", NoopIOSessionStrategy.INSTANCE)  
                .register("https", new SSLIOSessionStrategy(sslContext, new NoopHostnameVerifier()))  
                .build();
		
        // 配置io线程  
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()  
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())  
                .build();

        DefaultConnectingIOReactor ioreactor = null;
		try {
			ioreactor = new DefaultConnectingIOReactor(ioReactorConfig);
		} catch (IOReactorException e) {
			logger.error("初始化IO线程失败，" + ioReactorConfig.toString(), e);
			return;
		}
		
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioreactor, null, sessionStrategyRegistry, null);
		connManager.setMaxTotal(maxTotal);
		connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		if(maxPerRoute!=null && !maxPerRoute.isEmpty()) {
			for(Map.Entry<HttpRoute,Integer> entry : maxPerRoute.entrySet()) {
				connManager.setMaxPerRoute(entry.getKey(), entry.getValue());
			}
		}
		
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout)
				.setExpectContinueEnabled(expectContinueEnabled)
				.build();
		
		httpclient = HttpAsyncClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(new NoopCookieStore())
				.build();
		
		httpclient.start();
	}
	
	/**
	 * 释放资源，关闭http客户端
	 */
	@Override
	public void close() throws IOException {
		if(httpclient!=null) {
			httpclient.close();
		}
	}
	
	/**
	 * 重设请求的连接超时时间和数据获取超时时间<br>
	 * 若连接超时时间和数据获取超时时间都小于等于0，则不重设，直接返回原{@link RequestConfig}
	 */
	private RequestConfig rebuildRequestConfig(int connectTimeout, int socketTimeout) {
		if(connectTimeout==NOOP_RESET_TIMEOUT && socketTimeout==NOOP_RESET_TIMEOUT) {
			return this.requestConfig;
		}
		Builder builder = RequestConfig.copy(requestConfig);
		if(connectTimeout>0) {
			builder.setConnectTimeout(connectTimeout);
		}
		if(socketTimeout>0) {
			builder.setSocketTimeout(socketTimeout);
		}
		return builder.build();
	}
	
	//执行异步请求
	private Future<HttpResponse> execute(HttpUriRequest request, Charset encoding, AysncHandler handler){
		
		return httpclient.execute(request, new FutureCallback<HttpResponse>() {

			@Override
			public void completed(HttpResponse result) {
				HttpEntity entity = result.getEntity();
				if(entity!=null) {
					InputStream instream = null;  
                    try {  
                    	instream = entity.getContent();
                        String body = IOUtils.toString(instream, encoding);
                        if(handler!=null) {
                        	handler.completed(body);
                        }
                    } catch(UnsupportedOperationException | IOException e) {
                    	if(handler!=null) {
                    		handler.failed(e);
                    	}
                    } finally {  
                    	IOUtils.closeQuietly(instream);
                    }  
				}
			}

			@Override
			public void failed(Exception ex) {
				if(handler!=null) {
					handler.failed(ex);
				}
			}

			@Override
			public void cancelled() {
				if(handler!=null) {
					handler.cancelled();
				}
			}
			
		});
	}
	
	private Future<HttpResponse> get(String uri, Header[] headers, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		HttpGet get = new HttpGet(uri);
		
		RequestConfig config = rebuildRequestConfig(connectTimeout, socketTimeout);
		get.setConfig(config);
		
		if(ArrayUtils.isNotEmpty(headers)) {
			get.setHeaders(headers);
		}
		
		return execute(get, encoding, handler);
	}
	
	/**
	 * 发送GET请求
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGet(String uri, Charset encoding, AysncHandler handler) throws IOException {
		return sendGet(uri, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGet(String uri, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		return get(uri, null, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送GET请求，可以携带header参数
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGetWithHeader(String uri, Map<String, String> headers, Charset encoding, AysncHandler handler) throws IOException {
		return sendGetWithHeader(uri, headers, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求，可以携带header参数
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGetWithHeader(String uri, Map<String, String> headers, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			return get(uri, headerList.toArray(new Header[headerList.size()]), encoding, handler, connectTimeout, socketTimeout);
		}
		return get(uri, null, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送GET请求，可以携带cookie
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGetWithCookie(String uri, Map<String, String> cookies, Charset encoding, AysncHandler handler) throws IOException {
		return sendGetWithCookie(uri, cookies, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送GET请求，可以携带cookie
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendGetWithCookie(String uri, Map<String, String> cookies, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		if (cookies != null && !cookies.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			for(Map.Entry<String,String> cookieEntry : cookies.entrySet()) {
				buffer.append(cookieEntry.getKey())
					.append("=")
					.append(cookies.get(cookieEntry.getValue()))
					.append("; ");
			}
			// 设置cookie内容
			Header header = new BasicHeader("Cookie", buffer.toString());
			return get(uri, new Header[] {header}, encoding, handler, connectTimeout, socketTimeout);
		}
		return get(uri, null, encoding, handler, connectTimeout, socketTimeout);
	}
	
	
	private Future<HttpResponse> post(String uri, Header[] headers, HttpEntity entity, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		HttpPost post = new HttpPost(uri);
		RequestConfig config = rebuildRequestConfig(connectTimeout, socketTimeout);
		post.setConfig(config);
		if(ArrayUtils.isNotEmpty(headers)) {
			post.setHeaders(headers);
		}
		if(entity!=null) {
			post.setEntity(entity);
		}
		return execute(post, encoding, handler);
	}
	
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Charset encoding, AysncHandler handler) throws IOException {
		return sendPost(uri, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		return post(uri, null, null, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithHeader(String uri, Map<String, String> headers, Charset encoding, AysncHandler handler) throws IOException {
		return sendPostWithHeader(uri, headers, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithHeader(String uri, Map<String, String> headers, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			return post(uri, headerList.toArray(new Header[headerList.size()]), null, encoding, handler, connectTimeout, socketTimeout);
		}
		return post(uri, null, null, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithCookie(String uri, Map<String, String> cookies, Charset encoding, AysncHandler handler) throws IOException {
		return sendPostWithCookie(uri, cookies, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param cookies cookie参数，为空时表示不携带cookie
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithCookie(String uri, Map<String, String> cookies, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		if (cookies != null && !cookies.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			for(Map.Entry<String,String> cookieEntry : cookies.entrySet()) {
				buffer.append(cookieEntry.getKey())
					.append("=")
					.append(cookies.get(cookieEntry.getValue()))
					.append("; ");
			}
			// 设置cookie内容
			Header header = new BasicHeader("Cookie", buffer.toString());
			return post(uri, new Header[] {header}, null, encoding, handler, connectTimeout, socketTimeout);
		}
		return post(uri, null, null, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithParam(String uri, Map<String, String> params, Charset encoding, AysncHandler handler) throws IOException {
		return sendPost(uri, null, params, encoding, handler);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithParam(String uri, Map<String, String> params, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		return sendPost(uri, null, params, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param params 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithParam(String uri, String params, Charset encoding, AysncHandler handler) throws IOException {
		return sendPost(uri, null, params, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param params 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPostWithParam(String uri, String params, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		return sendPost(uri, null, params, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Map<String, String> headers, Map<String, String> params, Charset encoding, AysncHandler handler) throws IOException {
		return sendPost(uri, headers, params, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param params body参数，为空时表示不携带参数
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Map<String, String> headers, Map<String, String> params, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		Header[] headerArray = null;
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			headerArray = headerList.toArray(new Header[headerList.size()]);
		}
		HttpEntity paramEntity = null;
		if (params!=null && !params.isEmpty()) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> param : params.entrySet()) {
				paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
			paramEntity = new UrlEncodedFormEntity(paramList, encoding);
		}
		return post(uri, headerArray, paramEntity, encoding, handler, connectTimeout, socketTimeout);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param data 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Map<String, String> headers, String data, Charset encoding, AysncHandler handler) throws IOException {
		return sendPost(uri, headers, data, encoding, handler, NOOP_RESET_TIMEOUT, NOOP_RESET_TIMEOUT);
	}
	/**
	 * 发送POST请求
	 * @param uri 请求地址，可带查询参数
	 * @param headers header参数，为空时表示不携带header
	 * @param data 请求body，为空时表示不携带body
	 * @param encoding 结果转换成字符串时的编码
	 * @param handler 异步回调类
	 * @param connectTimeout 当次请求的连接超时时间
	 * @param socketTimeout 当次请求的数据传输超时时间
	 * @return 返回异步结果，若调用{@link Future#get()}来获取结果会阻塞线程
	 * @throws IOException
	 */
	public Future<HttpResponse> sendPost(String uri, Map<String, String> headers, String data, Charset encoding, AysncHandler handler, int connectTimeout, int socketTimeout) throws IOException {
		Header[] headerArray = null;
		if (headers != null && !headers.isEmpty()) {
			List<Header> headerList = new ArrayList<>();
			for(Map.Entry<String,String> headerEntry : headers.entrySet()) {
				headerList.add(new BasicHeader(headerEntry.getKey(), headerEntry.getValue()));
			}
			headerArray = headerList.toArray(new Header[headerList.size()]);
		}
		HttpEntity paramEntity = null;
		if (StringUtils.isNotBlank(data)) {
			paramEntity = new StringEntity(data, encoding);
		}
		return post(uri, headerArray, paramEntity, encoding, handler, connectTimeout, socketTimeout);
	}
	
}
