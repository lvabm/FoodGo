package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemSubCategoryFilterRequest(String name, Integer categoryId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalCategoryId() {
    return Optional.ofNullable(categoryId);
  }
}
