package com.foodgo.backend.module.maps.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Request for reverse geocoding (coordinates to address)
 */
@Data
public class ReverseGeocodeRequest {
  @NotNull(message = "Vĩ độ không được để trống")
  private BigDecimal latitude;
  
  @NotNull(message = "Kinh độ không được để trống")
  private BigDecimal longitude;
  
  private String language = "vi"; // Vietnamese language
}


