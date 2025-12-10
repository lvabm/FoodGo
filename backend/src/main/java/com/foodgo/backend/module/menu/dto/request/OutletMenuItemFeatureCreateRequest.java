package com.foodgo.backend.module.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OutletMenuItemFeatureCreateRequest(
    @NotNull Integer featureId, @NotBlank String value // Giá trị tùy chọn (ví dụ: "Large", "2")
    ) {}
