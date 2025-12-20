package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemFeatureFilterRequest(String name, String featureType) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalFeatureType() {
    return Optional.ofNullable(featureType).filter(s -> !s.isBlank());
  }
}
