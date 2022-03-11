package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class ServiceResult<T> implements Serializable {
    private String code;
    private T data;
    private String token;
    private String msg;

    private ServiceResult() {}

    public static <T> ServiceResult<T> success(T result, HttpServletRequest request) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "1";
        item.data = result;
        item.token = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
        item.msg = "";
        return item;
    }

    public static <T> ServiceResult<T> success(T result, String msg, HttpServletRequest request) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "1";
        item.data = result;
        item.token = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
        item.msg = msg;
        return item;
    }

    public static <T> ServiceResult<T> loginSuccess(T result, String token) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "1";
        item.data = result;
        item.token = token;
        item.msg = "登录成功";
        return item;
    }

    public static <T> ServiceResult<T> noLogin(T result, HttpServletRequest request) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "401";
        item.data = result;
        item.token = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
        item.msg = "token异常，请重新登录";
        return item;
    }

    public static <T> ServiceResult<T> failure(T result, HttpServletRequest request) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "0";
        item.data = result;
        item.token = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
        item.msg = "数据处理异常！";
        return item;
    }

    public static <T> ServiceResult<T> failure(T result, String errorMsg, HttpServletRequest request) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = "0";
        item.data = result;
        item.token = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");
        item.msg = errorMsg;
        return item;
    }

    public String getCode() {
        return code;
    }

    public boolean hasData() {
        return data != null;
    }

    public T getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public String getMsg() {
        return msg;
    }
}
