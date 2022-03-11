package com.example.demo.interceptor;

import com.example.demo.entity.User;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.JwtTokenUtilPlusJ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String jwtToken = request.getHeader("Authorization");
    System.out.println(jwtToken);
    if (jwtToken == null || jwtToken== "") {
      HttpUtil.responseWrite(request, response);
      return false;
    }
    User user = JwtTokenUtilPlusJ.parseToken(jwtToken, User.class);
    System.out.println(user);
//    if(user == null){
//      HttpUtil.responseWrite(request, response);
//      return false;
//    }
      return true;
  }
}

