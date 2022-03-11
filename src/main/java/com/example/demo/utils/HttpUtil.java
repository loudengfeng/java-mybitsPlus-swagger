package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

  private static final List<Object> emptyList = new ArrayList<>();

  public static boolean isApiRequest(HttpServletRequest request) {
    return !request.getHeader("Accept").contains("text/html");
  }

  // 根据请求接收的类型定义不同的响应方式
  // 判断请求对象request里的header里accept字段接收类型
  // 如果是 text/html 则响应一段js，这里要将response对象的响应内容类型也设置成 text/javascript
  // 如果是 application/json 则响应一串json，response 对象的响应内容类型要设置成 application/json
  // 因为响应内容描述是中文，所以都要带上 ;charset=utf-8 否则会有乱码
  // 写注释真累费劲。。
  public static void responseWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println(request);
    if (!HttpUtil.isApiRequest(request)) {
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().write("<script>alert('请先登录!');window.history.go(-1);</script>");
    } else {
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().write(JsonUtil.objectToJson(emptyList));
    }
  }
}
