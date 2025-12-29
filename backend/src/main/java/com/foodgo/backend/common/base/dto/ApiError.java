package com.foodgo.backend.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
  @Builder.Default
  private boolean success = false;
  private String errorCode;
  private String message;
  private List<String> details;
  @Builder.Default
  private Instant timestamp = Instant.now();
}
