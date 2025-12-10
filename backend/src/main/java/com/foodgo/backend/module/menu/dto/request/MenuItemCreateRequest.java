package com.foodgo.backend.module.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MenuItemCreateRequest(
    @NotBlank @Size(max = 255) String name,
    String description,
    @NotNull Integer subCategoryId, // ID của MenuItemSubCategory
    @NotNull Integer provinceId // ID của Province áp dụng
    ) {}
