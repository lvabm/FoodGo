package com.foodgo.backend.module.outlet.dto.request.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

public record OperatingHoursCreateRequest(
    @NotNull @Min(0) @Max(6) Integer dayOfWeek, // 0: CN, 1: T2, ...
    @NotNull LocalTime openTime,
    @NotNull LocalTime closeTime,
    Boolean isClosed,
    UUID outletId // Tham chiếu Outlet
    ) {}
