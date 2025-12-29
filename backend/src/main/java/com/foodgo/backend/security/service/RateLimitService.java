package com.foodgo.backend.security.service;

import com.foodgo.backend.common.constant.AppConstants;
import com.foodgo.backend.common.exception.TooManyRequestsException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service to manage rate limiting using Caffeine cache.
 * Tracks request counts per identifier (username or IP) within time windows.
 */
@Slf4j
@Service
public class RateLimitService {

  // Cache for per-minute rate limiting
  private final Cache<String, Integer> minuteRateLimitCache;

  // Cache for per-hour rate limiting
  private final Cache<String, Integer> hourRateLimitCache;

  public RateLimitService() {
    // Initialize caches with TTL matching the rate limit window
    this.minuteRateLimitCache = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .maximumSize(10_000) // Max 10k unique identifiers
        .build();

    this.hourRateLimitCache = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .maximumSize(10_000) // Max 10k unique identifiers
        .build();
  }

  /**
   * Check if the request should be allowed based on rate limits.
   * 
   * @param identifier User identifier (username or IP address)
   * @param path Request path (for logging)
   * @throws TooManyRequestsException if rate limit is exceeded
   */
  public void checkRateLimit(@NonNull String identifier, @NonNull String path) {
    // Check per-minute limit
    int minuteCount = minuteRateLimitCache.get(identifier, key -> 0);
    if (minuteCount >= AppConstants.RATE_LIMIT_REQUESTS_PER_MINUTE) {
      int retryAfter = 60; // Retry after 1 minute
      throw new TooManyRequestsException(
          String.format(
              "Rate limit exceeded: %d requests per minute. Please try again later.",
              AppConstants.RATE_LIMIT_REQUESTS_PER_MINUTE
          ),
          retryAfter
      );
    }

    // Check per-hour limit
    int hourCount = hourRateLimitCache.get(identifier, key -> 0);
    if (hourCount >= AppConstants.RATE_LIMIT_REQUESTS_PER_HOUR) {
      int retryAfter = 3600; // Retry after 1 hour
      throw new TooManyRequestsException(
          String.format(
              "Rate limit exceeded: %d requests per hour. Please try again later.",
              AppConstants.RATE_LIMIT_REQUESTS_PER_HOUR
          ),
          retryAfter
      );
    }

    // Increment counters
    minuteRateLimitCache.put(identifier, minuteCount + 1);
    hourRateLimitCache.put(identifier, hourCount + 1);

    log.debug("Rate limit check passed for identifier: {} (minute: {}/{}, hour: {}/{})",
        identifier,
        minuteCount + 1,
        AppConstants.RATE_LIMIT_REQUESTS_PER_MINUTE,
        hourCount + 1,
        AppConstants.RATE_LIMIT_REQUESTS_PER_HOUR
    );
  }

  /**
   * Get current rate limit statistics for an identifier
   */
  public RateLimitStats getRateLimitStats(@NonNull String identifier) {
    int minuteCount = minuteRateLimitCache.get(identifier, key -> 0);
    int hourCount = hourRateLimitCache.get(identifier, key -> 0);

    return new RateLimitStats(
        minuteCount,
        AppConstants.RATE_LIMIT_REQUESTS_PER_MINUTE,
        hourCount,
        AppConstants.RATE_LIMIT_REQUESTS_PER_HOUR
    );
  }

  /**
   * Reset rate limit for an identifier (admin function)
   */
  public void resetRateLimit(@NonNull String identifier) {
    minuteRateLimitCache.invalidate(identifier);
    hourRateLimitCache.invalidate(identifier);
    log.info("Rate limit reset for identifier: {}", identifier);
  }

  /**
   * Clear all rate limit data (admin function)
   */
  public void clearAllRateLimits() {
    minuteRateLimitCache.invalidateAll();
    hourRateLimitCache.invalidateAll();
    log.info("All rate limits cleared");
  }

  /**
   * Data class for rate limit statistics
   */
  public record RateLimitStats(
      int minuteCount,
      int minuteLimit,
      int hourCount,
      int hourLimit
  ) {
    public boolean isMinuteLimitExceeded() {
      return minuteCount >= minuteLimit;
    }

    public boolean isHourLimitExceeded() {
      return hourCount >= hourLimit;
    }
  }
}


