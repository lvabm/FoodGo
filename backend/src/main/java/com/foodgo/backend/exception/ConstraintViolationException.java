package com.foodgo.backend.exception;

import com.foodgo.backend.common.base.BaseException;
import com.foodgo.backend.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class ConstraintViolationException extends BaseException {
    public ConstraintViolationException(ErrorCode errorCode, String message) {super(errorCode, message, HttpStatus.BAD_REQUEST);}
    public ConstraintViolationException( String message) {super(ErrorCode.BAD_REQUEST, message, HttpStatus.BAD_REQUEST);}
}
