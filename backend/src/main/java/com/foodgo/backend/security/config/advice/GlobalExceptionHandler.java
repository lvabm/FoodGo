package com.foodgo.backend.security.config.advice;

import com.foodgo.backend.common.base.ApiError;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.DataConflictException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant; // 👈 Import cần thiết cho timestamp
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ApiError buildErrorResponse(String errorCode, String message, List<String> details) {
    return ApiError.builder()
        .success(false)
        .errorCode(errorCode)
        .message(message)
        .details(details)
        .timestamp(Instant.now())
        .build();
  }

  // 1. Xử lý các lỗi Validation (@Valid) - HTTP Status 400 Bad Request
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {

    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                error -> {
                  String fieldName =
                      (error instanceof FieldError) ? ((FieldError) error).getField() : "object";
                  return fieldName + ": " + error.getDefaultMessage();
                })
            .collect(Collectors.toList());

    // 🎯 Tự xây dựng ApiError
    ApiError errorResponse =
        buildErrorResponse(
            "VALIDATION_ERROR",
            "Dữ liệu đầu vào không hợp lệ. Vui lòng kiểm tra các trường.",
            errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 2. Xử lý CHUNG các Custom Business Exceptions (400, 409, etc.)
  @ExceptionHandler({
    BadRequestException.class,
    DataConflictException.class,
  })
  public ResponseEntity<ApiError> handleBusinessExceptions(
      RuntimeException ex, WebRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    String errorCode = "BAD_REQUEST";

    if (ex instanceof DataConflictException) {
      status = HttpStatus.CONFLICT;
      errorCode = "DATA_CONFLICT";
    }
    // Thêm các phân loại khác nếu cần...

    // 🎯 Tự xây dựng ApiError
    ApiError errorResponse =
        buildErrorResponse(
            errorCode,
            ex.getMessage(), // Lấy message chi tiết từ Exception
            Collections.singletonList("Lỗi nghiệp vụ đã xảy ra.")); // Chi tiết có thể ẩn bớt

    return new ResponseEntity<>(errorResponse, status);
  }

  // 3. Xử lý 404 Not Found
  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ApiError> handleNotFoundExceptions(
      RuntimeException ex, WebRequest request) {

    // 🎯 Tự xây dựng ApiError
    ApiError errorResponse =
        buildErrorResponse(
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            Collections.singletonList("Tài nguyên yêu cầu không được tìm thấy."));

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // 4. Xử lý lỗi hệ thống chung (500 Internal Server Error)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {

    // ⚠️ Nên log lỗi 500 chi tiết ở đây: logger.error("Internal Server Error: ", ex);

    // 🎯 Tự xây dựng ApiError
    ApiError errorResponse =
        buildErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Đã xảy ra lỗi hệ thống không mong muốn.",
            Collections.singletonList("Vui lòng liên hệ hỗ trợ.")); // Ẩn chi tiết lỗi

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
