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

import java.io.IOException;

/**
 * Filter để thêm các response headers cho caching, security và performance.
 * 
 * <p>Headers được thêm:
 * <ul>
 *   <li>Cache-Control: Kiểm soát caching behavior</li>
 *   <li>X-Request-ID: Unique identifier cho mỗi request (cho tracing)</li>
 *   <li>X-Response-Time: Thời gian xử lý request</li>
 * </ul>
 * 
 * @author FoodGo Team
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(2) // Sau RateLimitFilter, trước JWT filter
public class ResponseHeaderFilter extends OncePerRequestFilter {

  private static final String REQUEST_ID_HEADER = "X-Request-ID";
  private static final String RESPONSE_TIME_HEADER = "X-Response-Time";
  private static final String CACHE_CONTROL_HEADER = "Cache-Control";

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    long startTime = System.currentTimeMillis();
    String requestId = generateRequestId(request);

    // Thêm request ID vào response header
    response.setHeader(REQUEST_ID_HEADER, requestId);

    // Set cache control headers dựa trên endpoint
    setCacheControlHeaders(request, response);

    try {
      filterChain.doFilter(request, response);
    } finally {
      // Tính toán và thêm response time
      long duration = System.currentTimeMillis() - startTime;
      response.setHeader(RESPONSE_TIME_HEADER, duration + "ms");

      // Log slow requests
      if (duration > 1000) {
        log.warn("Slow request detected: {} {} - {}ms", 
            request.getMethod(), request.getRequestURI(), duration);
      }
    }
  }

  /**
   * Generate unique request ID for tracing
   */
  private String generateRequestId(HttpServletRequest request) {
    // Sử dụng request ID từ header nếu có (từ load balancer/proxy)
    String existingRequestId = request.getHeader(REQUEST_ID_HEADER);
    if (existingRequestId != null && !existingRequestId.isEmpty()) {
      return existingRequestId;
    }
    
    // Generate new request ID
    return java.util.UUID.randomUUID().toString();
  }

  /**
   * Set cache control headers based on endpoint type
   */
  private void setCacheControlHeaders(HttpServletRequest request, HttpServletResponse response) {
    String path = request.getRequestURI();
    String method = request.getMethod();

    // Static resources và public data - có thể cache
    if (path.startsWith("/api/v1/location") && "GET".equals(method)) {
      // Location data (countries, provinces, districts) - cache 1 hour
      response.setHeader(CACHE_CONTROL_HEADER, "public, max-age=3600");
    } else if (path.startsWith("/api/v1/outlet-types") && "GET".equals(method)) {
      // Outlet types - cache 30 minutes
      response.setHeader(CACHE_CONTROL_HEADER, "public, max-age=1800");
    } else if (path.startsWith("/api/v1/outlet-categories") && "GET".equals(method)) {
      // Outlet categories - cache 30 minutes
      response.setHeader(CACHE_CONTROL_HEADER, "public, max-age=1800");
    } else if (path.startsWith("/api/v1/health") && "GET".equals(method)) {
      // Health checks - no cache
      response.setHeader(CACHE_CONTROL_HEADER, "no-cache, no-store, must-revalidate");
    } else if (path.startsWith("/api/v1/auth") || path.startsWith("/api/v1/bookings") 
        || path.startsWith("/api/v1/reviews") || path.startsWith("/api/v1/outlets")) {
      // User-specific data - no cache
      response.setHeader(CACHE_CONTROL_HEADER, "no-cache, no-store, must-revalidate");
    } else {
      // Default: no cache for API endpoints
      response.setHeader(CACHE_CONTROL_HEADER, "no-cache");
    }
  }
}


