package com.foodgo.backend.module.booking.dto.request.create;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record BookingCreateRequest(
    @NotNull(message = "Vui lòng chọn cửa hàng") UUID outletId,
    @NotNull(message = "Vui lòng chọn ngày đặt")
        @FutureOrPresent(message = "Ngày đặt phải từ hôm nay trở đi")
        LocalDate bookingDate,
    @NotNull(message = "Vui lòng chọn giờ đặt") LocalTime bookingTime,
    @NotNull(message = "Vui lòng nhập số lượng khách")
        @Min(value = 1, message = "Số lượng khách tối thiểu là 1")
        Integer numberOfGuests,
    String userNotes) {}
