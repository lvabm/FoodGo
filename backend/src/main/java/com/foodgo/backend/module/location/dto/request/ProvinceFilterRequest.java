package com.foodgo.backend.module.location.dto.request;

import java.util.Optional;

public record ProvinceFilterRequest(String name, Integer countryId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalCountryId() {
    return Optional.ofNullable(countryId);
  }
}
