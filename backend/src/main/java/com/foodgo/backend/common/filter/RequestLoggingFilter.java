package com.foodgo.backend.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(1)
public class RequestLoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Skip logging for health check and static resources
    String path = request.getRequestURI();
    if (path.startsWith("/api/v1/health") || 
        path.startsWith("/swagger-ui") || 
        path.startsWith("/v3/api-docs") ||
        path.startsWith("/actuator")) {
      filterChain.doFilter(request, response);
      return;
    }

    long startTime = System.currentTimeMillis();
    
    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

    try {
      filterChain.doFilter(wrappedRequest, wrappedResponse);
    } finally {
      long duration = System.currentTimeMillis() - startTime;
      logRequestAndResponse(wrappedRequest, wrappedResponse, duration);
      if (wrappedResponse != null) {
        wrappedResponse.copyBodyToResponse();
      }
    }
  }

  private void logRequestAndResponse(
      ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response,
      long duration) {

    String method = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    String fullUri = queryString == null ? uri : uri + "?" + queryString;
    int status = response.getStatus();

    // Log request
    if (log.isDebugEnabled()) {
      String requestBody = getRequestBody(request);
      String responseBody = getResponseBody(response);
      
      log.debug("Request: {} {} | Headers: {} | Body: {}", 
          method, fullUri, getRequestHeaders(request), requestBody);
      log.debug("Response: Status {} | Duration: {}ms | Body: {}", 
          status, duration, responseBody);
    } else {
      // Info level logging - chỉ log method, URI, status và duration
      log.info("{} {} | Status: {} | Duration: {}ms", 
          method, fullUri, status, duration);
    }

    // Log slow requests (> 1 second)
    if (duration > 1000) {
      log.warn("Slow request detected: {} {} took {}ms", method, fullUri, duration);
    }

    // Log errors (4xx, 5xx)
    if (status >= 400) {
      log.warn("Error response: {} {} | Status: {} | Duration: {}ms", 
          method, fullUri, status, duration);
    }
  }

  private String getRequestBody(ContentCachingRequestWrapper request) {
    byte[] content = request.getContentAsByteArray();
    if (content.length > 0) {
      return new String(content, StandardCharsets.UTF_8);
    }
    return "";
  }

  private String getResponseBody(ContentCachingResponseWrapper response) {
    byte[] content = response.getContentAsByteArray();
    if (content.length > 0) {
      String body = new String(content, StandardCharsets.UTF_8);
      // Limit response body logging to 1000 characters
      return body.length() > 1000 ? body.substring(0, 1000) + "..." : body;
    }
    return "";
  }

  private String getRequestHeaders(ContentCachingRequestWrapper request) {
    return Collections.list(request.getHeaderNames()).stream()
        .filter(headerName -> !headerName.equalsIgnoreCase("authorization"))
        .map(headerName -> headerName + "=" + request.getHeader(headerName))
        .collect(Collectors.joining(", "));
  }
}

