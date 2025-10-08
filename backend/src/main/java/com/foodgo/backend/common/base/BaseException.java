package com.foodgo.backend.common.base;

import com.foodgo.backend.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
  private final ErrorCode errorCode;
  private final HttpStatus httpStatus;

  public BaseException(ErrorCode errorCode, String message, HttpStatus httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }
}
