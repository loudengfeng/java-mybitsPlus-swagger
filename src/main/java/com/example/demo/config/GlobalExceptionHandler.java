package com.example.demo.config;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author Lenovo
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private final List<Object> emptyList = new ArrayList<>();

  @ExceptionHandler(Exception.class)
  public R handler(Exception e) {
    e.printStackTrace();
    return R.failed("服务器内部错误：" + e.getMessage());
  }
  @ExceptionHandler(MyException.class)
  public R handlerMyException(Exception e) {
    e.printStackTrace();
    return R.failed (e.getMessage());
  }

  /**
   * 校验错误拦截处理
   *
   * @param exception 错误信息集合
   * @return 错误信息
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public R<Object> validationBodyException(MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();
    String message = "";
    if (result.hasErrors()) {
      List<ObjectError> errors = result.getAllErrors();
      if (errors != null) {
        errors.forEach(p -> {
          FieldError fieldError = (FieldError) p;
                    /*log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");*/

        });
        if (errors.size() > 0) {
          FieldError fieldError = (FieldError) errors.get(0);
          message = fieldError.getField() + ":" + fieldError.getDefaultMessage();
        }
      }
    }
    return R.failed("".equals(message) ? "请填写正确信息" : message);
  }

  /**
   * 参数类型转换错误
   *
   * @param exception 错误
   * @return 错误信息
   */
  @ExceptionHandler(HttpMessageConversionException.class)
  public R<Object> parameterTypeException(HttpMessageConversionException exception) {
    return R.failed("参数类型错误" + exception.getMessage());

  }

  /**
   * 参数缺失
   *
   * @param exception 错误
   * @return 错误信息
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public R<Object> missingParameterException(MissingServletRequestParameterException exception) {
    return R.failed("参数缺失:" + exception.getParameterName());

  }

  /**
   * 参数缺失
   *
   * @param exception 错误
   * @return 错误信息
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public R<Object> constraintViolationException(ConstraintViolationException exception) {
    return R.failed("参数缺失:" + exception.getMessage());

  }


  /**
   * 请求方式错误
   *
   * @param exception 错误
   * @return 错误信息
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public R<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
    return R.failed(exception.getMessage());
  }
  /**
   * ...
   *
   * @param exception 错误
   * @return 错误信息
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public R<Object> httpRequestMethodNotSupportedException2(MethodArgumentTypeMismatchException exception) {
    return R.failed("参数类型错误");
  }
}
