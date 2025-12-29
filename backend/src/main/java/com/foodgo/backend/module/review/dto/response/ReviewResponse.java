package com.foodgo.backend.module.review.dto.response;

import com.foodgo.backend.common.constant.ModerationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReviewResponse(
    UUID id,
    UUID bookingId,
    UUID userId,
    String userName,
    String userAvatar,
    UUID outletId,
    String outletName,
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
    LocalDateTime createdAt,
    ModerationStatus moderationStatus,
    String moderationReason,
    LocalDateTime moderatedAt,
    UUID moderatedBy) {
  
  // Default values for backward compatibility
  public ReviewResponse {
    if (moderationStatus == null) {
      moderationStatus = ModerationStatus.PENDING;
    }
  }
  public record ReplyResponse(String ownerName, String replyText, LocalDateTime createdAt) {}
}
