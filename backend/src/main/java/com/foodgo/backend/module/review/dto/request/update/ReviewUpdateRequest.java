package com.foodgo.backend.module.review.dto.request.update;

import java.util.List;

public record ReviewUpdateRequest(
    Integer foodRating,
    Integer serviceRating,
    Integer ambianceRating,
    Integer priceRating,
    String comment,
    List<Long> deletedImageIds, // Danh sách ID ảnh muốn xóa
    List<String> newImageUrls // Danh sách URL ảnh mới muốn thêm
    ) {}
