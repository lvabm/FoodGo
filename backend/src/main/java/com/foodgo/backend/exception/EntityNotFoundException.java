package com.foodgo.backend.exception;

import com.foodgo.backend.common.base.BaseException;
import org.springframework.http.HttpStatus;
import com.foodgo.backend.common.constant.ErrorCode;


public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(ErrorCode errorCode,String message) {super(errorCode, message, HttpStatus.NOT_FOUND);}
}
