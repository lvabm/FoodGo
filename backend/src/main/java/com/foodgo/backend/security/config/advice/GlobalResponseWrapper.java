package com.foodgo.backend.security.config.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.base.dto.PageResponse;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Tự động bọc mọi DTO trả về thành công vào BaseResponse/PageResponse. Đọc message từ
 * SuccessMessageContext.
 */
@RestControllerAdvice(basePackages = "com.foodgo.backend")
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

  private final ObjectMapper objectMapper = new ObjectMapper(); // Dùng cho trường hợp trả về String
  private static final String DEFAULT_SUCCESS_MESSAGE = "Thao tác thành công.";

  @Override
  public boolean supports(
      MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    // KHÔNG bọc nếu Controller trả về ResponseEntity (vì nó đã kiểm soát hoàn toàn)
    // và KHÔNG bọc nếu nó là các lớp lỗi (ApiError, BaseResponse lỗi...)
    return !returnType.getParameterType().equals(ResponseEntity.class);
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {

    // 1. Nếu body đã là BaseResponse hoặc null (ví dụ: ControllerAdvice trả về)
    if (body == null || body instanceof BaseResponse) {
      return body;
    }

    // --- Xử lý Lấy Message ---
    String dynamicMessage = SuccessMessageContext.getMessage();
    String finalMessage =
        (dynamicMessage != null && !dynamicMessage.isEmpty())
            ? dynamicMessage
            : DEFAULT_SUCCESS_MESSAGE;

    // 2. Lấy Status Code hiện tại
    int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
    HttpStatus httpStatus = HttpStatus.valueOf(status);

    // --- Bắt trường hợp đối tượng phân trang Page<T> ---
    if (body instanceof Page<?> springPage) {
      SuccessMessageContext.clear(); // Clear ngay sau khi sử dụng

      // Tự động chuyển đổi Page<T> (Spring Data) sang PageResponse<T> (của bạn)
      return new PageResponse<>(
          springPage.getNumber(),
          springPage.getSize(),
          springPage.getTotalElements(),
          springPage.getTotalPages(),
          finalMessage, // Sử dụng message động
          springPage.getContent());
    }
    // --------------------------------------------------

    // --- Bắt trường hợp DTO đơn lẻ ---
    BaseResponse<Object> wrappedResponse =
        BaseResponse.builder()
            .success(httpStatus.is2xxSuccessful())
            .message(finalMessage) // Sử dụng message động
            .data(body)
            .build();

    // Clear ThreadLocal (tùy chọn, Interceptor đã lo nhưng clear ở đây cũng an toàn)
    SuccessMessageContext.clear();

    // Xử lý trường hợp đặc biệt: Controller trả về String
    if (body instanceof String) {
      try {
        // Phải trả về chuỗi JSON của BaseResponse, không phải đối tượng BaseResponse
        return objectMapper.writeValueAsString(wrappedResponse);
      } catch (JsonProcessingException e) {
        // Log lỗi và trả về BaseResponse gốc
        return wrappedResponse;
      }
    }

    return wrappedResponse;
  }
}
