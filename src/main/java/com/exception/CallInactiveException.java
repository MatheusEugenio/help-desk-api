package com.exception;

public class CallInactiveException extends RuntimeException {
  public CallInactiveException(String message) {
    super(message);
  }
}
