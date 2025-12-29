package com.foodgo.backend.module.outlet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response DTO for Outlet with distance information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutletWithDistanceResponse {
  private UUID id;
  private String name;
  private String description;
  private String address;
  private String email;
  private String phoneNumber;
  private String website;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private String priceRange;
  private Integer capacity;
  private Boolean isActive;
  private BigDecimal averageRating;
  private Integer totalReviews;
  
  // Distance information
  private BigDecimal distanceKm; // Distance in kilometers
  private Integer distanceMeters; // Distance in meters
  private String distanceText; // Human-readable distance (e.g., "1.2 km")
  private Integer durationSeconds; // Travel time in seconds
  private String durationText; // Human-readable duration (e.g., "5 phút")
  
  // Additional outlet info
  private Integer districtId;
  private String districtName;
  private Integer outletTypeId;
  private String outletTypeName;
}


