package com.foodgo.backend.module.menu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record OutletMenuItemCreateRequest(
    @NotBlank String name,
    String description,
    @NotNull @DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0") BigDecimal price,
    String imageUrl,
    UUID outletId, // ID Outlet
    @NotNull UUID menuItemId // ID của món ăn gốc
    ) {}
