package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemCategoryFilterRequest(String name, Integer typeId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalTypeId() {
    return Optional.ofNullable(typeId);
  }
}
