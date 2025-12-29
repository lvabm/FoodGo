package com.foodgo.backend.security.config;

import com.foodgo.backend.security.config.interceptor.SuccessMessageCleanupInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final SuccessMessageCleanupInterceptor cleanupInterceptor;

  @Autowired
  public WebMvcConfig(SuccessMessageCleanupInterceptor cleanupInterceptor) {
    this.cleanupInterceptor = cleanupInterceptor;
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    // Đăng ký Interceptor để áp dụng cho mọi request ("/**")
    registry.addInterceptor(cleanupInterceptor).addPathPatterns("/**");
  }
}
