package com.foodgo.backend.module.outlet.dto.request.update;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalTime;
import java.util.Optional;

public record OperatingHoursUpdateRequest(
    @Min(0) @Max(6) Integer dayOfWeek, LocalTime openTime, LocalTime closeTime, Boolean isClosed) {
  public Optional<Integer> optionalDayOfWeek() {
    return Optional.ofNullable(dayOfWeek);
  }

  public Optional<LocalTime> optionalOpenTime() {
    return Optional.ofNullable(openTime);
  }

  public Optional<LocalTime> optionalCloseTime() {
    return Optional.ofNullable(closeTime);
  }

  public Optional<Boolean> optionalIsClosed() {
    return Optional.ofNullable(isClosed);
  }
}
