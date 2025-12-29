package com.foodgo.backend.module.outlet.dto.request.update;

import java.util.Optional;

public record OutletCategoryUpdateRequest(
    String name, String description, Integer typeId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalDescription() {
    return Optional.ofNullable(description).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalTypeId() {
    return Optional.ofNullable(typeId);
  }
}



