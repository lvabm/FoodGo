package com.foodgo.backend.module.booking.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record BookingResponse(
    UUID id,
    UUID outletId,
    String outletName,
    String outletAddress,
    UUID userId,
    String customerName,
    String customerPhone,
    LocalDate bookingDate,
    LocalTime bookingTime,
    Integer numberOfGuests,
    String status,
    BigDecimal depositAmount,
    String userNotes,
    String ownerNotes,
    Instant userCheckedInAt,
    Instant ownerCheckedInAt,
    boolean isReviewable // Helper field: User có được viết review không?
    ) {}
