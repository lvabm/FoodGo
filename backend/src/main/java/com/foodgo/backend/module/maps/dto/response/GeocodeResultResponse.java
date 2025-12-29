package com.foodgo.backend.module.maps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Simplified response for geocoding result
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeResultResponse {
  private String formattedAddress;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private String placeId;
  private String status;
  private String message;
}


