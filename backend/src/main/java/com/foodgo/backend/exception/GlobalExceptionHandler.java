package com.foodgo.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // 1 Bắt lỗi validation (DTO & Entity) | ErrorCode : 400
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {
    String path = request.getDescription(false);

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());

    List<String> errors = ex.getBindingResult().getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

    body.put("errors", errors);
    body.put("path", path);
    return new ResponseEntity<>(body, status);
  }

  // 2 Bắt lỗi dữ liệu ràng buộc trong Controller, không cần gọi thủ công, hàm này tự động được gọi khi sử dụng các Annotation sau: (@NotNull | @Size | @Min | @Max | @Email) - (Controller) | ErrorCode : 400
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(
          ConstraintViolationException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 3 Bắt lỗi dữ liệu không tìm thấy - (Service) | ErrorCode : 400
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFound(
          EntityNotFoundException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 4 Bắt lỗi dữ liệu đầu vào không hợp lệ (sai định dạng, thiếu tham số bắt buộc, vd : Password >= 6 kí tự ) - (Service) | ErrorCode : 400
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequest(
          BadRequestException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 5 Bắt lỗi xung đột dữ liệu (Dữ liệu trùng, xung đột) - (Service) | ErrorCode : 409
  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Object> handleConflict(
          ConflictException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 6 Bắt lỗi không có quyền truy cập (UserDetail ko có quyền truy cập URL) - (Service) | ErrorCode : 403
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Object> handleForbidden(
          ForbiddenException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 7 Bắt lỗi client yêu cầu một entity, record, hoặc tài nguyên mà không tồn tại trong hệ thống (VD: /users/100 nhưng user ID 100 không có trong DB) - (Service) | ErrorCode : 404
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFound(
          ResourceNotFoundException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 7 Bắt lỗi UserDetail chưa đăng nhập  - (Service) | ErrorCode : 401
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorized(
          UnauthorizedException ex,
          HttpServletRequest request) {
    ex.setPath(request.getRequestURI());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getHttpStatus().value());
    body.put("errorCode", ex.getErrorCode());
    body.put("message", ex.getMessage());
    body.put("path",ex.getPath());
    return new ResponseEntity<>(body, ex.getHttpStatus());
  }

  // 8 Bắt tất cả lỗi còn lại
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("message", ex.getMessage());
    body.put("path", request.getRequestURI());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}



