package com.foodgo.backend.module.location.dto.request.update;

import java.util.Optional;

public record CountryUpdateRequest(String name, String code) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalCode() {
    return Optional.ofNullable(code).filter(s -> !s.isBlank());
  }
}

