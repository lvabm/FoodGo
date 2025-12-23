package com.foodgo.backend.module.booking.dto;

import com.foodgo.backend.common.constant.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingResponse {
    private UUID id;
    private Instant bookingTime;
    private int numberOfPeople;
    private BookingStatus status;
    private String note;

    private UUID userId;
    private String username;

    private UUID outletId;
    private String outletName;
}
