package com.foodgo.backend.exception;

import com.foodgo.backend.common.dto.ApiError;
import com.foodgo.backend.util.ApiResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError; // 👈 Import cần thiết
import org.springframework.web.bind.MethodArgumentNotValidException; // 👈 Import cần thiết
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors; // 👈 Import cần thiết

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Giả định các lớp Custom Exception khác nằm trong package com.foodgo.backend.exception.
  // Ví dụ: InvalidInputException, DataConflictException, NotEnoughBalanceException

  /** 1. Xử lý các lỗi Validation (@Valid) - HTTP Status 400 Bad Request */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {

    // 🔑 Lấy tất cả thông báo lỗi chi tiết từ các trường bị lỗi
    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                error -> {
                  String fieldName =
                      (error instanceof FieldError) ? ((FieldError) error).getField() : "object";
                  return fieldName + ": " + error.getDefaultMessage();
                })
            .collect(Collectors.toList());

    ApiError errorResponse =
        ApiResponseBuilder.error(
            "VALIDATION_ERROR",
            "Dữ liệu đầu vào không hợp lệ.",
            errors); // 🔑 errors chứa danh sách chi tiết lỗi

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400
  }

  // --- Nhóm 4xx (Client Errors) ---

  /** 2. Xử lý CHUNG các Custom Business Exceptions (400, 409, etc.) */
  @ExceptionHandler({
    BadRequestException.class, // 400 - Dữ liệu yêu cầu thiếu/sai
    DataConflictException.class, // 409 - Xung đột khóa (ví dụ: email đã tồn tại)
    // Thêm các Custom Exception khác (ví dụ: ForbiddenException.class, UnauthorizedException.class)
  })
  public ResponseEntity<ApiError> handleBusinessExceptions(
      RuntimeException ex, WebRequest request) {

    // 🔑 Phân loại trạng thái HTTP dựa trên loại Exception cụ thể
    HttpStatus status = HttpStatus.BAD_REQUEST; // Mặc định là 400
    String errorCode = "BAD_REQUEST";

    if (ex instanceof DataConflictException) {
      status = HttpStatus.CONFLICT;
      errorCode = "DATA_CONFLICT";
    }
    // Thêm các phân loại khác (ví dụ: if (ex instanceof UnauthorizedException) status =
    // HttpStatus.UNAUTHORIZED;)

    ApiError errorResponse =
        ApiResponseBuilder.error(
            errorCode,
            ex.getMessage(), // Sử dụng thông báo chi tiết từ Service
            Collections.singletonList("Xảy ra lỗi nghiệp vụ: " + ex.getMessage()));

    return new ResponseEntity<>(errorResponse, status);
  }

  /** 3. Xử lý 404 Not Found (Tách riêng để đảm bảo trạng thái HTTP) */
  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ApiError> handleNotFoundExceptions(
      RuntimeException ex, WebRequest request) {

    ApiError errorResponse =
        ApiResponseBuilder.error(
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            Collections.singletonList("The requested resource could not be found."));

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // 404
  }

  // --- Lỗi Server ---

  /** 4. Xử lý lỗi hệ thống chung (500 Internal Server Error) */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {

    // ⚠️ Luôn log chi tiết lỗi 500 ở đây (System log)

    ApiError errorResponse =
        ApiResponseBuilder.error(
            "INTERNAL_SERVER_ERROR",
            "Đã xảy ra lỗi hệ thống không mong muốn.",
            Collections.singletonList(
                "Please contact support with timestamp.")); // Không nên expose lỗi chi tiết 500 ra
    // ngoài

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500
  }
}
