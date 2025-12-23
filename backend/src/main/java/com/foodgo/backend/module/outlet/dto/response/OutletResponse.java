package com.foodgo.backend.module.outlet.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OutletResponse(
    UUID id,
    String name,
    String description,
    String address,
    String email,
    String phoneNumber,
    BigDecimal latitude,
    BigDecimal longitude,
    String priceRange,
    Integer capacity,
    boolean isActive,
    BigDecimal averageRating,
    Integer totalReviews,
    UUID ownerId, // Chủ sở hữu
    String districtName, // Từ District Entity
    String outletTypeName, // Từ OutletType Entity
    List<String> images // Danh sách URL ảnh của outlet
    ) {}
