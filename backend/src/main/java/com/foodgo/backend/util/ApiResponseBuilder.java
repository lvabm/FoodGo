package com.foodgo.backend.util;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.ApiError;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
public final class ApiResponseBuilder {

  public static <T> BaseResponse<T> success(String message, T data) {
    return BaseResponse.<T>builder()
        .success(true)
        .message(message)
        .data(data)
        .timestamp(Instant.now())
        .build();
  }

  public static ApiError error(String errorCode, String message, List<String> details) {
    return ApiError.builder()
        .success(false)
        .errorCode(errorCode)
        .message(message)
        .details(details)
        .timestamp(Instant.now())
        .build();
  }
}
