package com.foodgo.backend.module.outlet.dto.request.update;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record OutletMenuItemUpdateRequest(
    String name,
    String description,
    BigDecimal price,
    String imageUrl,
    Boolean isAvailable,
    // Chỉ cho phép cập nhật lại MenuItem gốc nếu cần (ví dụ: đổi tham chiếu)
    UUID menuItemId) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalDescription() {
    return Optional.ofNullable(description).filter(s -> !s.isBlank());
  }

  public Optional<BigDecimal> optionalPrice() {
    return Optional.ofNullable(price);
  }

  public Optional<String> optionalImageUrl() {
    return Optional.ofNullable(imageUrl).filter(s -> !s.isBlank());
  }

  public Optional<Boolean> optionalIsAvailable() {
    return Optional.ofNullable(isAvailable);
  }

  public Optional<UUID> optionalMenuItemId() {
    return Optional.ofNullable(menuItemId);
  }
}
