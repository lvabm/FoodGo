package com.foodgo.backend.module.outlet.dto.request.filter;

import java.util.Optional;

public record OutletFeatureFilterRequest(String name) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }
}
