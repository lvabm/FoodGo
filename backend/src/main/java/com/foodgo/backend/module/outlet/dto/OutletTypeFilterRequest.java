package com.foodgo.backend.module.outlet.dto;

import java.util.Optional;

public record OutletTypeFilterRequest(String name) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }
}
