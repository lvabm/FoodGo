package com.foodgo.backend.module.user.dto.request.update;

import java.time.LocalDate;
import java.util.Optional;

public record ProfileUpdateRequest(
    String fullName, LocalDate dateOfBirth, String address, String avatarUrl, Integer countryId) {
  public Optional<String> optionalFullName() {
    return Optional.ofNullable(fullName).filter(s -> !s.isBlank());
  }

  public Optional<LocalDate> optionalDateOfBirth() {
    return Optional.ofNullable(dateOfBirth);
  }

  public Optional<String> optionalAddress() {
    return Optional.ofNullable(address).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalAvatarUrl() {
    return Optional.ofNullable(avatarUrl).filter(s -> !s.isBlank());
  }

  public Optional<Integer> optionalCountryId() {
    return Optional.ofNullable(countryId);
  }
}
