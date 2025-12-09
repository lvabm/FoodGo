package com.foodgo.backend.module.menu.dto.request;

import java.util.Optional;

public record MenuItemTypeFilterRequest(String name) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }
}
