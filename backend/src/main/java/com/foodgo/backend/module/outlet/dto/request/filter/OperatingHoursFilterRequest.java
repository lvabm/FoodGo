package com.foodgo.backend.module.outlet.dto.request.filter;

import java.util.Optional;
import java.util.UUID;

public record OperatingHoursFilterRequest(UUID outletId, Integer dayOfWeek) {
  public Optional<UUID> optionalOutletId() {
    return Optional.ofNullable(outletId);
  }

  public Optional<Integer> optionalDayOfWeek() {
    return Optional.ofNullable(dayOfWeek);
  }
}
