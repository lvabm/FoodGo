package com.foodgo.backend.module.review.dto.request.create;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record ReviewCreateRequest(
    @NotNull(message = "Booking ID là bắt buộc") UUID bookingId,
    @Min(1) @Max(5) Integer foodRating,
    @Min(1) @Max(5) Integer serviceRating,
    @Min(1) @Max(5) Integer ambianceRating,
    @Min(1) @Max(5) Integer priceRating,
    @Size(max = 1000, message = "Nội dung đánh giá không quá 1000 ký tự") String comment,
    List<String> imageUrls // Danh sách link ảnh upload xong
    ) {}
