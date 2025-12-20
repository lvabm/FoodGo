package com.foodgo.backend.module.outlet.dto.request.filter;

import java.util.Optional;

public record OutletCategoryFilterRequest(String name, Integer typeId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalTypeId() {
    return Optional.ofNullable(typeId);
  }
}
