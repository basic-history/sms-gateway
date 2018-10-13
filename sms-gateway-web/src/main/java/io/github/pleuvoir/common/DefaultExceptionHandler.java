package io.github.pleuvoir.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import io.github.pleuvoir.common.RspCode;

/**
 * 提供通用的异常处理，包括打印异常、输出统一的错误信息
 *
 */
public class DefaultExceptionHandler extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	
	private static final String SPACE = StringUtils.repeat(StringUtils.SPACE, 4);
	
	//错误的json信息
	private Map<String,String> defaultErrorJson = new ConcurrentHashMap<>();
	
	/**
	 * 设置json返回时的错误json信息
	 * @param defaultErrorJson
	 */
	public void setDefaultErrorJson(Map<String,String> defaultErrorJson) {
		this.defaultErrorJson = defaultErrorJson;
	}
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		logger.error(buildLogMessage(ex, request));
	}
	
	@Override
	protected String buildLogMessage(Exception ex, HttpServletRequest request) {
		StringBuilder b = new StringBuilder();
		b.append(super.buildLogMessage(ex, request));
		
		b.append(StringUtils.CR);
		b.append(StringUtils.LF);
		
		StackTraceElement[] sts = ex.getStackTrace();
		if(ArrayUtils.isNotEmpty(sts)){
			for(StackTraceElement st : sts){
				b.append(SPACE);
				b.append(st.toString());
				b.append(StringUtils.CR);
				b.append(StringUtils.LF);
			}
		}
		return b.toString();
	}
	
	/**
	 * 根据controller方法上是否存在ResponseBody注解来决定返回的错误信息使用web页面还是json
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		boolean isReturnJson = isReturnJson(handler);

		if (isReturnJson) {
			FastJsonJsonView view = new FastJsonJsonView();
			// 未设置json返回时的错误信息
			if (defaultErrorJson == null || defaultErrorJson.isEmpty()) {
				Map<String, String> attrs = new LinkedHashMap<>();
				attrs.put("code", RspCode.ERROR.getCode());
				attrs.put("msg", RspCode.ERROR.getMsg());
				view.setAttributesMap(attrs);
			} else {
				view.setAttributesMap(defaultErrorJson);
			}
			ModelAndView v = new ModelAndView();
			v.setView(view);
			return v;
		}
		return super.doResolveException(request, response, handler, ex);
	}
	
	
	private boolean isReturnJson(Object handler) {
/*		if(handler instanceof HandlerMethod) {
			//检查是否存在ResponseBody注解
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			//判断方法上是否有ResponseBody注解
			ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if(responseBody!=null) {
				return true;
			}
			//判断类上是否有ResponseBody注解
			responseBody = handlerMethod.getBean().getClass().getAnnotation(ResponseBody.class);
			if(responseBody!=null) {
				return true;
			}
			//判断类上是否有RestController注解
			RestController restController = handlerMethod.getBean().getClass().getAnnotation(RestController.class);
			if(restController!=null) {
				return true;
			}
		}*/
		return true;
	}
}
