package com.foodgo.backend.module.booking.dto.request.filter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record BookingFilterRequest(
    String status,
    UUID outletId,
    UUID userId,
    String bookingDateFrom,
    String bookingDateTo,
    String searchTerm,
    String viewType) { // "MY_BOOKINGS" or "MANAGE_BOOKINGS"

  public Optional<String> optionalStatus() {
    return Optional.ofNullable(status).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalViewType() {
    return Optional.ofNullable(viewType).filter(s -> !s.isBlank());
  }

  public Optional<UUID> optionalOutletId() {
    return Optional.ofNullable(outletId);
  }

  public Optional<UUID> optionalUserId() {
    return Optional.ofNullable(userId);
  }

  public Optional<LocalDate> optionalDateFrom() {
    return Optional.ofNullable(bookingDateFrom)
        .filter(s -> !s.isBlank())
        .map(LocalDate::parse); // Có thể throw Exception nếu format sai, GlobalHandler sẽ bắt
  }

  public Optional<LocalDate> optionalDateTo() {
    return Optional.ofNullable(bookingDateTo).filter(s -> !s.isBlank()).map(LocalDate::parse);
  }

  public Optional<String> optionalSearchTerm() {
    return Optional.ofNullable(searchTerm).filter(s -> !s.isBlank());
  }
}
