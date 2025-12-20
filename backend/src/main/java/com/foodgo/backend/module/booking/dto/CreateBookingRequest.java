package com.foodgo.backend.module.booking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateBookingRequest {

    @NotNull(message = "Booking time không được để trống")
    private Instant bookingTime;

    @Min(value = 1, message = "Số lượng người phải lớn hơn 0")
    private int numberOfPeople;

    @Size(max = 500, message = "Ghi chú tối đa 500 ký tự")
    private String note;

    @NotNull(message = "UserId không được để trống")
    private UUID userId;

    @NotNull(message = "OutletId không được để trống")
    private UUID outletId;
}
