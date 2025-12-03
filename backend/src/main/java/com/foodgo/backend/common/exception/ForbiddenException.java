package com.foodgo.backend.common.exception;

import com.foodgo.backend.common.base.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
  public ForbiddenException(String message) {
    super(ErrorCode.FORBIDDEN, message, HttpStatus.FORBIDDEN);
  }

  public ForbiddenException(ErrorCode errorCode, String message) {
    super(errorCode, message, HttpStatus.FORBIDDEN);
  }
}
