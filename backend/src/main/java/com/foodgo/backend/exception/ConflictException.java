package com.foodgo.backend.exception;

import com.foodgo.backend.common.base.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
  public ConflictException(String message) {
    super(ErrorCode.CONFLICT, message, HttpStatus.CONFLICT);
  }

  public ConflictException(ErrorCode errorCode, String message) {
    super(errorCode, message, HttpStatus.CONFLICT);
  }
}
