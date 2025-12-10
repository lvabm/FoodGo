package com.foodgo.backend.module.menu.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OutletMenuItemResponse(
    Integer id,
    UUID outletId,
    String outletName,
    UUID menuItemId,
    String menuItemName, // Tên MenuItem gốc
    String name,
    String description,
    BigDecimal price,
    String imageUrl,
    Boolean isAvailable) {}
