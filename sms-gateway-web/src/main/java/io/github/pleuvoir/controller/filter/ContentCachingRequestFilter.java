package io.github.pleuvoir.controller.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.pleuvoir.common.bean.CachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于多次读取body中的信息
 */
@WebFilter(value="/*", filterName = "contentCachingRequest")
public class ContentCachingRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //请求头Content-Type:multipart/form-data 时不缓存body
        if(MediaType.MULTIPART_FORM_DATA_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))){
            filterChain.doFilter(request, response);
        }else{
            CachingRequestWrapper wrapper = new CachingRequestWrapper(request);
            filterChain.doFilter(wrapper, response);
        }
    }

}
