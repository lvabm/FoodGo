package com.foodgo.backend.common.exception;

import com.foodgo.backend.common.base.dto.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class DataConflictException extends BaseException {
  public DataConflictException(String message) {
    super(ErrorCode.CONFLICT, message, HttpStatus.CONFLICT);
  }

  public DataConflictException(ErrorCode errorCode, String message) {
    super(errorCode, message, HttpStatus.CONFLICT);
  }
}
