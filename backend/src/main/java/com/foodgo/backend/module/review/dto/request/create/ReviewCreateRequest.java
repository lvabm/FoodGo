package com.foodgo.backend.module.review.dto.request.create;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record ReviewCreateRequest(
    @NotNull(message = "Booking ID là bắt buộc") UUID bookingId,
    @NotNull(message = "Đánh giá đồ ăn là bắt buộc") @Min(1) @Max(5) Integer foodRating,
    @NotNull(message = "Đánh giá phục vụ là bắt buộc") @Min(1) @Max(5) Integer serviceRating,
    @NotNull(message = "Đánh giá không gian là bắt buộc") @Min(1) @Max(5) Integer ambianceRating,
    @NotNull(message = "Đánh giá giá cả là bắt buộc") @Min(1) @Max(5) Integer priceRating,
    @Size(max = 1000, message = "Nội dung đánh giá không quá 1000 ký tự") String comment,
    List<String> imageUrls // Danh sách link ảnh upload xong
    ) {}
