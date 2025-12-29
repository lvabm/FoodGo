package com.foodgo.backend.module.maps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simplified response for distance calculation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistanceResultResponse {
  private String originAddress;
  private String destinationAddress;
  private String distanceText;
  private Integer distanceMeters;
  private String durationText;
  private Integer durationSeconds;
  private String status;
  private String message;
}


