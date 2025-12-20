package com.foodgo.backend.module.outlet.dto.response;

import java.time.LocalTime;
import java.util.UUID;

public record OperatingHoursResponse(
    Integer id,
    Integer dayOfWeek,
    LocalTime openTime,
    LocalTime closeTime,
    Boolean isClosed,
    UUID outletId,
    String outletName) {}
