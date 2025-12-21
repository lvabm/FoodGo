package com.foodgo.backend.module.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReviewResponse(
    UUID id,
    UUID bookingId,
    UUID userId,
    String userName,
    String userAvatar,
    Integer overallRating,
    Integer foodRating,
    Integer serviceRating,
    Integer ambianceRating,
    Integer priceRating,
    String comment,
    List<String> images,
    Integer likesCount,
    Integer dislikesCount,
    ReplyResponse reply, // Nested Record cho câu trả lời
    LocalDateTime createdAt) {
  public record ReplyResponse(String ownerName, String replyText, LocalDateTime createdAt) {}
}
