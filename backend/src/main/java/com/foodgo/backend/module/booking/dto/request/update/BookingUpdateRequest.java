package com.foodgo.backend.module.booking.dto.request.update;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingUpdateRequest(
    LocalDate bookingDate,
    LocalTime bookingTime,
    Integer numberOfGuests,
    String userNotes,
    String status) {}
