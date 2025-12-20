package com.foodgo.backend.module.location.dto.request;

import java.util.Optional;

public record DistrictFilterRequest(String name, Integer provinceId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalProvinceId() {
    return Optional.ofNullable(provinceId);
  }
}
