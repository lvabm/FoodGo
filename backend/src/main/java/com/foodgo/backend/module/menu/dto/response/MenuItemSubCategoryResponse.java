package com.foodgo.backend.module.menu.dto.response;

public record MenuItemSubCategoryResponse(
    Integer id,
    String name,
    String description,
    Integer categoryId,
    String categoryName // Lấy từ MenuItemCategory Entity
    ) {}
