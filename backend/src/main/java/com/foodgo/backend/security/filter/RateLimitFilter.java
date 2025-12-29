package com.foodgo.backend.security.filter;

import com.foodgo.backend.common.exception.TooManyRequestsException;
import com.foodgo.backend.security.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to enforce rate limiting on API requests.
 * Checks rate limits before processing the request.
 */
@Slf4j
@Component
@Order(1) // Execute before JWT filter
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

  private final RateLimitService rateLimitService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Skip rate limiting for health checks and public endpoints
    String path = request.getRequestURI();
    if (shouldSkipRateLimit(path)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      // Get identifier (username if authenticated, IP if not)
      String identifier = getIdentifier(request);
      String requestUri = request.getRequestURI();
      
      if (identifier == null || requestUri == null) {
        // If we can't identify the user or get the URI, skip rate limiting
        filterChain.doFilter(request, response);
        return;
      }

      // Check rate limit
      rateLimitService.checkRateLimit(identifier, requestUri);

      // Continue with the request
      filterChain.doFilter(request, response);

    } catch (TooManyRequestsException e) {
      log.warn("Rate limit exceeded for identifier: {} on path: {}", getIdentifier(request), path);
      response.setStatus(429); // HTTP 429 Too Many Requests
      response.setContentType("application/json");
      response.setHeader("Retry-After", String.valueOf(e.getRetryAfterSeconds()));
      response.getWriter().write(
          String.format(
              "{\"error\":\"Too Many Requests\",\"message\":\"%s\",\"retryAfter\":%d}",
              e.getMessage(),
              e.getRetryAfterSeconds()
          )
      );
    } catch (Exception e) {
      log.error("Error in rate limit filter", e);
      // On error, allow the request to proceed (fail open)
      filterChain.doFilter(request, response);
    }
  }

  /**
   * Get identifier for rate limiting (username if authenticated, IP if not)
   */
  private String getIdentifier(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if (authentication != null && authentication.isAuthenticated() 
        && !authentication.getPrincipal().equals("anonymousUser")) {
      // Use username for authenticated users
      return authentication.getName();
    }
    
    // Use IP address for anonymous users
    return getClientIpAddress(request);
  }

  /**
   * Extract client IP address from request
   */
  private String getClientIpAddress(HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      // Take the first IP in the chain
      return xForwardedFor.split(",")[0].trim();
    }
    
    String xRealIp = request.getHeader("X-Real-IP");
    if (xRealIp != null && !xRealIp.isEmpty()) {
      return xRealIp;
    }
    
    return request.getRemoteAddr();
  }

  /**
   * Check if rate limiting should be skipped for this path
   */
  private boolean shouldSkipRateLimit(String path) {
    // Skip rate limiting for:
    // - Health checks
    // - Swagger/OpenAPI docs
    // - Static resources
    return path.startsWith("/api/v1/health")
        || path.startsWith("/v3/api-docs")
        || path.startsWith("/swagger-ui")
        || path.startsWith("/swagger-ui.html");
  }
}

