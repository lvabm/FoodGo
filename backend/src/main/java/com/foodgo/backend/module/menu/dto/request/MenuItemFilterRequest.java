package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemFilterRequest(
    String name, Integer subCategoryId, Integer provinceId, Boolean isPopular) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalSubCategoryId() {
    return Optional.ofNullable(subCategoryId);
  }

  public Optional<Integer> optionalProvinceId() {
    return Optional.ofNullable(provinceId);
  }

  public Optional<Boolean> optionalIsPopular() {
    return Optional.ofNullable(isPopular);
  }
}
