package com.tavi.tavi_mrs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class ApiLogger extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        log(request,response, requestId);
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        request.setAttribute("requestId", requestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.info("requestId {}, Handle :{} , request take time: {}",request.getAttribute("requestId"), handler, executeTime);
    }

    private void log(HttpServletRequest request, HttpServletResponse response, String requestId) {
        logger.info("requestId {}, host {}  HttpMethod: {}, URI : {}",requestId, request.getHeader("host"),
                request.getMethod(), request.getRequestURI() );
    }
}
