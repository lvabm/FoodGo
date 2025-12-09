package com.foodgo.backend.module.location.dto.request;

import java.util.Optional;

public record CountryFilterRequest(String name) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }
}
