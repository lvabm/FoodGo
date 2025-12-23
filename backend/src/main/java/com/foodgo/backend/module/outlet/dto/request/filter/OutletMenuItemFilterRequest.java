package com.foodgo.backend.module.outlet.dto.request.filter;

import java.util.Optional;
import java.util.UUID;

public record OutletMenuItemFilterRequest(String name, UUID outletId, UUID menuItemId, Boolean isAvailable) {
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<UUID> optionalOutletId() {
    return Optional.ofNullable(outletId);
  }

  public Optional<UUID> optionalMenuItemId() {
    return Optional.ofNullable(menuItemId);
  }

  public Optional<Boolean> optionalIsAvailable() {
    return Optional.ofNullable(isAvailable);
  }
}
