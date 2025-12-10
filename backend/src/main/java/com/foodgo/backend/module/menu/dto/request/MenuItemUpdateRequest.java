package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemUpdateRequest(
    String name, String description, Boolean isPopular, Integer subCategoryId, Integer provinceId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalDescription() {
    return Optional.ofNullable(description).filter(s -> !s.isBlank());
  }

  public Optional<Boolean> optionalIsPopular() {
    return Optional.ofNullable(isPopular);
  }

  public Optional<Integer> optionalSubCategoryId() {
    return Optional.ofNullable(subCategoryId);
  }

  public Optional<Integer> optionalProvinceId() {
    return Optional.ofNullable(provinceId);
  }
}
