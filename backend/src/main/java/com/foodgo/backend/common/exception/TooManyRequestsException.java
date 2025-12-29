package com.foodgo.backend.common.exception;

import lombok.Getter;

/**
 * Exception thrown when rate limit is exceeded.
 */
@Getter
public class TooManyRequestsException extends RuntimeException {
  
  private final int retryAfterSeconds;

  public TooManyRequestsException(String message, int retryAfterSeconds) {
    super(message);
    this.retryAfterSeconds = retryAfterSeconds;
  }
}


