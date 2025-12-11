package com.foodgo.backend.module.user.dto.request.filter;

import java.util.Optional;
import java.util.UUID;

public record ProfileFilterRequest(
    String fullName,
    UUID userId, // Admin có thể tìm kiếm theo User ID
    Integer countryId) {
  public Optional<String> optionalFullName() {
    return Optional.ofNullable(fullName).filter(s -> !s.isBlank());
  }

  public Optional<UUID> optionalUserId() {
    return Optional.ofNullable(userId);
  }

  public Optional<Integer> optionalCountryId() {
    return Optional.ofNullable(countryId);
  }
}
