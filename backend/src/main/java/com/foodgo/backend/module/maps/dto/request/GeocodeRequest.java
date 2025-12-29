package com.foodgo.backend.module.maps.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request for geocoding (address to coordinates)
 */
@Data
public class GeocodeRequest {
  @NotBlank(message = "Địa chỉ không được để trống")
  private String address;
  
  private String language = "vi"; // Vietnamese language
  private String region = "VN"; // Vietnam region
}


