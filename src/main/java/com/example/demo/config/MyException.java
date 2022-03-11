package com.example.demo.config;

public class MyException extends RuntimeException {
  private String message;

  public MyException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
