package com.foodgo.backend.module.outlet.dto.request;

import java.util.List;
import java.util.Optional;

public record OutletFilterRequest(
    String name,
    Integer districtId,
    Integer outletTypeId,
    String priceRange,
    List<Integer> featureIds, // Lọc Many-to-Many
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
}
