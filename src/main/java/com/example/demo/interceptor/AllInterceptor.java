package com.example.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@Slf4j
public class AllInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    log.info("--------------请求的服务路径为：" + request.getRequestURI() + "--------------------");
    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = (String) headerNames.nextElement();
      log.info("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
    }
    Enumeration params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String paramName = (String) params.nextElement();
      log.info("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
    }
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    log.info("--------------服务请求结束：" + request.getRequestURI() + "--------------------");
  }
}
