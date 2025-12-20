package com.foodgo.backend.module.menu.dto.response;

public record MenuItemCategoryResponse(
    Integer id,
    String name,
    String description,
    Integer typeId,
    String typeName // Lấy từ MenuItemType Entity
    ) {}
