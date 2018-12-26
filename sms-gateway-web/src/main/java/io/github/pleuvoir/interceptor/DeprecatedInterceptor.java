package io.github.pleuvoir.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.github.pleuvoir.message.common.RspCode;
import io.github.pleuvoir.message.model.vo.ResultMessageVO;

/**
 * 废弃接口拦截器<br/>
 * 调用了controller方法上标注了{@link Deprecated}的接口均会返回接口已废除的错误
 */
public class DeprecatedInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(DeprecatedInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("【废弃接口拦截器】进入拦截器，url:{}", request.getRequestURI());
		}

		if(isDeprecated(handler)){
			logger.warn("【废弃接口拦截器】调用了废弃的接口，url:{}", request.getRequestURI());
			responseTo(response);
			return false;
		}
		return true;
	}


	private boolean isDeprecated(Object handler) {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Deprecated deprecated = handlerMethod.getMethodAnnotation(Deprecated.class);
			return deprecated != null;
		}
		return false;
	}


	@SuppressWarnings("rawtypes")
	private void responseTo(HttpServletResponse response) throws IOException {
		ResultMessageVO vo = new ResultMessageVO(RspCode.DEPRECATED_INTERFACE);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().write(vo.toJSON());
	}
}
