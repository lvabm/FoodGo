package com.foodgo.backend.exception;

import com.foodgo.backend.common.base.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
  public UnauthorizedException(String message) {
    super(ErrorCode.UNAUTHORIZED, message, HttpStatus.UNAUTHORIZED);
  }

  public UnauthorizedException(ErrorCode errorCode, String message) {
    super(errorCode, message, HttpStatus.UNAUTHORIZED);
  }
}
