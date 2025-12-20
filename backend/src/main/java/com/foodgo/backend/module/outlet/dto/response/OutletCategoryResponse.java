package com.foodgo.backend.module.outlet.dto.response;

public record OutletCategoryResponse(
    Integer id,
    String name,
    String description,
    Integer typeId,
    String typeName // Lấy từ OutletType Entity
    ) {}
