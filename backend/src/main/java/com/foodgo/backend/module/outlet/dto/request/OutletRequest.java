package com.foodgo.backend.module.outlet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record OutletRequest(
    @NotBlank @Size(max = 255) String name,
    String description,
    @NotBlank String address,
    String email,
    String phoneNumber,
    String website,
    BigDecimal latitude,
    BigDecimal longitude,
    @Size(max = 20) String priceRange,
    Integer capacity,
    @NotNull Integer typeId, // ID của OutletType
    @NotNull Integer districtId // ID của District
    ) {}
