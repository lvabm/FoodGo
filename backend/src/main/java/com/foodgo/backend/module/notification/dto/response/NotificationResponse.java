package com.foodgo.backend.module.notification.dto.response;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
    Long id,
    String type,
    String title,
    String content,
    UUID relatedId,
    Boolean isRead,
    UUID userId,
    Instant createdAt
) {}


