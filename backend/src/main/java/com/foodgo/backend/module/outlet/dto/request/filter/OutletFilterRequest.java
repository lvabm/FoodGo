package com.foodgo.backend.module.outlet.dto.request.filter;

import java.util.List;
import java.util.Optional;

public record OutletFilterRequest(
    String name,
    Integer districtId,
    Integer outletTypeId,
    String priceRange,
    List<Integer> featureIds, // Lọc Many-to-Many
    // Distance filter
    java.math.BigDecimal latitude,
    java.math.BigDecimal longitude,
    Double maxDistanceKm, // Maximum distance in kilometers
    String distanceMode, // driving, walking, bicycling, transit
    Integer page,
    Integer size) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalDistrictId() {
    return Optional.ofNullable(districtId);
  }

  public Optional<Integer> optionalOutletTypeId() {
    return Optional.ofNullable(outletTypeId);
  }

  public Optional<String> optionalPriceRange() {
    return Optional.ofNullable(priceRange).filter(s -> !s.isBlank());
  }

  public Optional<List<Integer>> optionalFeatureIds() {
    return Optional.ofNullable(featureIds).filter(l -> !l.isEmpty());
  }

  public Optional<java.math.BigDecimal> optionalLatitude() {
    return Optional.ofNullable(latitude);
  }

  public Optional<java.math.BigDecimal> optionalLongitude() {
    return Optional.ofNullable(longitude);
  }

  public Optional<Double> optionalMaxDistanceKm() {
    return Optional.ofNullable(maxDistanceKm);
  }

  public Optional<String> optionalDistanceMode() {
    return Optional.ofNullable(distanceMode).filter(s -> !s.isBlank());
  }
}
