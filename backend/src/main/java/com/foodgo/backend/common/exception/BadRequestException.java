package com.foodgo.backend.common.exception;

import com.foodgo.backend.common.base.dto.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
  public BadRequestException(String message) {
    super(ErrorCode.BAD_REQUEST, message, HttpStatus.BAD_REQUEST);
  }

  public BadRequestException(ErrorCode errorCode, String message) {
    super(errorCode, message, HttpStatus.BAD_REQUEST);
  }
}
