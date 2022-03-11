package com.example.demo.config;

import com.example.demo.interceptor.AllInterceptor;
import com.example.demo.interceptor.RequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther:
 * @Date: 2021/03/31/9:43
 * @Description:
 */
//@Configuration
class InterceptorConfig extends WebMvcConfigurationSupport {
  @Resource
  private RequestInterceptor requestInterceptor;
  @Resource
  private AllInterceptor allInterceptor;
  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    //拦截器1 拦截所有请求打印请求信息
    registry.addInterceptor(allInterceptor);
    //拦截器2 token验证
    // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
    // excludePathPatterns 用户排除拦截
    registry.addInterceptor(requestInterceptor)
      .addPathPatterns("/**")
      .excludePathPatterns("/user/login","/user/register","/captchaImage")
      .excludePathPatterns("/doc.html/**").excludePathPatterns("/webjars/**")
      .excludePathPatterns("/swagger-ui.html/**").excludePathPatterns("/webjars/**")
      .excludePathPatterns("/swagger-resources/**")
      .excludePathPatterns("/favicon.ico")
      .excludePathPatterns("/error")
      .excludePathPatterns("/swagger-resources");
    super.addInterceptors(registry);
  }
  @Override
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).forEach(cvt -> {
      MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) cvt;
      ObjectMapper objectMapper = converter.getObjectMapper();

      //添加rowid序列化器
      //SimpleModule simpleModule = new SimpleModule();
      //simpleModule.addSerializer(RowId.class, customRowIDSerializer);
      //objectMapper.registerModule(simpleModule);

      String dateFormat = "yyyy-MM-dd HH:mm:ss";
      objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
      objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    });
  }
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath*:/META-INF/resources/");

    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
