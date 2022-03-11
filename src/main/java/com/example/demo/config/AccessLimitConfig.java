package com.example.demo.config;

import com.example.demo.annotation.AccessLimit;
import com.example.demo.redis.RedisCache;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Component
public class AccessLimitConfig implements HandlerInterceptor {

  @Autowired
  private RedisCache redisCache;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
      AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
      if (null == accessLimit) {
        return true;
      }
      int seconds = accessLimit.seconds();
      int maxCount = accessLimit.maxCount();
      boolean needLogin = accessLimit.needLogin();

      if (needLogin) {
        //判断是否登录
      }

      String key = request.getContextPath() + ":" + request.getServletPath() + ":" + ip;

      Integer count = redisCache.getCacheObject(key);

      if (null == count || -1 == count) {
        redisCache.setCacheObject(key, 1, seconds, TimeUnit.MINUTES);
        return true;
      }
      System.out.println(count);
      System.out.println(maxCount);
      if (count < maxCount) {
        redisCache.incrCacheObject(key, 1);
        return true;
      }
        if (count >= maxCount) {
          throw new MyException("请求过于频繁请稍后再试");
        }
    }
    return true;
  }
}
