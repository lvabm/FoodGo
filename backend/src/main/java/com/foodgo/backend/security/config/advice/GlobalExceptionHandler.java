package com.foodgo.backend.security.config.advice;

import com.foodgo.backend.common.base.dto.ApiError;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.DataConflictException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.common.exception.TooManyRequestsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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

  private String getRequestInfo(WebRequest request) {
    if (request instanceof ServletWebRequest) {
      ServletWebRequest servletRequest = (ServletWebRequest) request;
      String method = servletRequest.getRequest().getMethod();
      String uri = servletRequest.getRequest().getRequestURI();
      return String.format("%s %s", method, uri);
    }
    // Fallback
    String method = request.getHeader("X-Forwarded-Method");
    if (method == null) {
      method = "UNKNOWN";
    }
    String uri = request.getDescription(false).replace("uri=", "");
    return String.format("%s %s", method, uri);
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

    String requestInfo = getRequestInfo(request);
    log.warn("Validation error at {}: {}", requestInfo, errors);

    ApiError errorResponse =
        buildErrorResponse(
            "VALIDATION_ERROR",
            "Dữ liệu đầu vào không hợp lệ. Vui lòng kiểm tra các trường.",
            errors);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 1.1. Xử lý lỗi Type Mismatch (ví dụ: UUID không hợp lệ)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiError> handleTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    Class<?> requiredType = ex.getRequiredType();
    String typeName = requiredType != null ? requiredType.getSimpleName() : "unknown";
    String errorMessage = String.format(
        "Giá trị '%s' không hợp lệ cho tham số '%s'. Kiểu dữ liệu mong đợi: %s",
        ex.getValue(), ex.getName(), typeName
    );
    
    log.warn("Type mismatch error at {}: {}", requestInfo, errorMessage);

    ApiError errorResponse = buildErrorResponse(
        "TYPE_MISMATCH_ERROR",
        errorMessage,
        Collections.singletonList("Vui lòng kiểm tra lại định dạng dữ liệu.")
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 1.2. Xử lý lỗi Missing Request Parameter
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiError> handleMissingParameter(
      MissingServletRequestParameterException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    String errorMessage = String.format(
        "Tham số bắt buộc '%s' (kiểu: %s) bị thiếu.",
        ex.getParameterName(), ex.getParameterType()
    );
    
    log.warn("Missing parameter error at {}: {}", requestInfo, errorMessage);

    ApiError errorResponse = buildErrorResponse(
        "MISSING_PARAMETER",
        errorMessage,
        Collections.singletonList("Vui lòng cung cấp đầy đủ các tham số bắt buộc.")
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 1.3. Xử lý lỗi HTTP Method Not Supported
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiError> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    String supportedMethods = ex.getSupportedMethods() != null 
        ? String.join(", ", ex.getSupportedMethods())
        : "N/A";
    
    String errorMessage = String.format(
        "Phương thức HTTP '%s' không được hỗ trợ. Các phương thức được hỗ trợ: %s",
        ex.getMethod(), supportedMethods
    );
    
    log.warn("Method not supported error at {}: {}", requestInfo, errorMessage);

    ApiError errorResponse = buildErrorResponse(
        "METHOD_NOT_SUPPORTED",
        errorMessage,
        Collections.singletonList("Vui lòng sử dụng phương thức HTTP đúng.")
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
  }

  // 1.4. Xử lý lỗi Constraint Violation (JPA/Jakarta Validation)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    
    List<String> errors = violations.stream()
        .map(violation -> {
          String propertyPath = violation.getPropertyPath().toString();
          String message = violation.getMessage();
          return propertyPath + ": " + message;
        })
        .collect(Collectors.toList());
    
    log.warn("Constraint violation error at {}: {}", requestInfo, errors);

    ApiError errorResponse = buildErrorResponse(
        "CONSTRAINT_VIOLATION",
        "Vi phạm ràng buộc dữ liệu. Vui lòng kiểm tra các trường.",
        errors
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // 2. Xử lý CHUNG các Custom Business Exceptions (400, 403, 409, etc.)
  @ExceptionHandler({
    BadRequestException.class,
    DataConflictException.class,
    ForbiddenException.class,
  })
  public ResponseEntity<ApiError> handleBusinessExceptions(
      RuntimeException ex, WebRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    String errorCode = "BAD_REQUEST";

    if (ex instanceof DataConflictException) {
      status = HttpStatus.CONFLICT;
      errorCode = "DATA_CONFLICT";
    } else if (ex instanceof ForbiddenException) {
      status = HttpStatus.FORBIDDEN;
      errorCode = "FORBIDDEN";
    }

    String requestInfo = getRequestInfo(request);
    log.warn("Business exception at {}: {} - {}", requestInfo, errorCode, ex.getMessage());

    ApiError errorResponse =
        buildErrorResponse(
            errorCode,
            ex.getMessage(),
            Collections.singletonList("Lỗi nghiệp vụ đã xảy ra."));

    return new ResponseEntity<>(errorResponse, status);
  }

  // 2.1. Xử lý lỗi Authentication (401)
  @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
  public ResponseEntity<ApiError> handleAuthenticationException(
      AuthenticationException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    log.warn("Authentication failed at {}: {}", requestInfo, ex.getMessage());

    ApiError errorResponse = buildErrorResponse(
        "AUTHENTICATION_ERROR",
        "Xác thực thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.",
        Collections.singletonList(ex.getMessage())
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  // 2.2. Xử lý lỗi Access Denied (403)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleAccessDeniedException(
      AccessDeniedException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    log.warn("Access denied at {}: {}", requestInfo, ex.getMessage());

    ApiError errorResponse = buildErrorResponse(
        "ACCESS_DENIED",
        "Bạn không có quyền truy cập tài nguyên này.",
        Collections.singletonList("Vui lòng liên hệ quản trị viên nếu bạn cần quyền truy cập.")
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  // 2.3. Xử lý lỗi Rate Limit (429 Too Many Requests)
  @ExceptionHandler(TooManyRequestsException.class)
  public ResponseEntity<ApiError> handleTooManyRequestsException(
      TooManyRequestsException ex, WebRequest request) {
    
    String requestInfo = getRequestInfo(request);
    log.warn("Rate limit exceeded at {}: {}", requestInfo, ex.getMessage());

    ApiError errorResponse = buildErrorResponse(
        "TOO_MANY_REQUESTS",
        ex.getMessage(),
        Collections.singletonList(
            String.format("Vui lòng thử lại sau %d giây.", ex.getRetryAfterSeconds())
        )
    );

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .header("Retry-After", String.valueOf(ex.getRetryAfterSeconds()))
        .body(errorResponse);
  }

  // 3. Xử lý 404 Not Found
  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ApiError> handleNotFoundExceptions(
      RuntimeException ex, WebRequest request) {

    String requestInfo = getRequestInfo(request);
    log.warn("Resource not found at {}: {}", requestInfo, ex.getMessage());

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

    String requestInfo = getRequestInfo(request);
    log.error("Internal server error at {}: {}", requestInfo, ex.getMessage(), ex);

    ApiError errorResponse =
        buildErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Đã xảy ra lỗi hệ thống không mong muốn.",
            Collections.singletonList("Vui lòng liên hệ hỗ trợ."));

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
