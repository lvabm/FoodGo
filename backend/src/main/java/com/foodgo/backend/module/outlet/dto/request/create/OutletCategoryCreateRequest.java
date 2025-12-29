package com.foodgo.backend.module.outlet.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OutletCategoryCreateRequest(
    @NotBlank @Size(max = 50) String name,
    @Size(max = 255) String description,
    @NotNull Integer typeId // ID của OutletType
    ) {}



