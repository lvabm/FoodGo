package com.foodgo.backend.module.maps.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Request for distance calculation
 */
@Data
public class DistanceRequest {
  @NotNull(message = "Vĩ độ điểm xuất phát không được để trống")
  private BigDecimal originLatitude;
  
  @NotNull(message = "Kinh độ điểm xuất phát không được để trống")
  private BigDecimal originLongitude;
  
  @NotNull(message = "Vĩ độ điểm đích không được để trống")
  private BigDecimal destinationLatitude;
  
  @NotNull(message = "Kinh độ điểm đích không được để trống")
  private BigDecimal destinationLongitude;
  
  private String mode = "driving"; // driving, walking, bicycling, transit
  private String language = "vi";
  private String unit = "metric"; // metric or imperial
}

