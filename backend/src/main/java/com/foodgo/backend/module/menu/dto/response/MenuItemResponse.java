package com.foodgo.backend.module.menu.dto.response;

import java.util.UUID;

public record MenuItemResponse(
    UUID id,
    String name,
    String description,
    Boolean isPopular,
    Integer subCategoryId,
    String subCategoryName,
    Integer provinceId,
    String provinceName) {}
