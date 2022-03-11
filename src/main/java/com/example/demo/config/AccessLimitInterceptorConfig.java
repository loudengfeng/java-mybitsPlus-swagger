package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// extends WebMvcConfigurerAdapter 已经废弃，java 8开始直接继承就可以
@Configuration
public class AccessLimitInterceptorConfig  implements WebMvcConfigurer {
  @Autowired
  private AccessLimitConfig accessLimitConfig;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(accessLimitConfig)
      .addPathPatterns("/**");
//      .addPathPatterns("/拦截路径")
//      .excludePathPatterns("/不被拦截路径 通常为登录注册或者首页");
  }
}
