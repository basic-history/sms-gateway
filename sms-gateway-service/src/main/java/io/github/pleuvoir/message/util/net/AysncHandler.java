package io.github.pleuvoir.message.util.net;

/**
 * 处理异步请求结果
 * @author abeir
 *
 */
public interface AysncHandler {
	/**
	 * 请求完成时被调用
	 * @param resultBody 响应结果的字符串 
	 */
	void completed(String resultBody);
	/**
	 * 请求时发生异常或请求完成时转换响应结果发生异常时被调用
	 * @param ex
	 */
    void failed(Exception ex);
    /**
     * 请求被取消时被调用
     */
    void cancelled();
}
