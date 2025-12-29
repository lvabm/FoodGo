package com.foodgo.backend.security.config.interceptor;

import com.foodgo.backend.common.context.SuccessMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor để đảm bảo SuccessMessageContext được dọn dẹp sau khi request hoàn thành, ngay cả khi
 * có Exception (sử dụng afterCompletion).
 */
@Component
public class SuccessMessageCleanupInterceptor implements HandlerInterceptor {

  @Override
  public void afterCompletion(
      @NonNull HttpServletRequest request, 
      @NonNull HttpServletResponse response, 
      @NonNull Object handler, 
      @Nullable Exception ex) {
    // Luôn gọi clear() sau khi mọi thứ đã hoàn tất (bao gồm cả ResponseBodyAdvice)
    SuccessMessageContext.clear();
  }
}
