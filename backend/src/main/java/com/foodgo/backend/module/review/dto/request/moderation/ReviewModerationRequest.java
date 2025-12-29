package com.foodgo.backend.module.review.dto.request.moderation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO cho các thao tác kiểm duyệt review
 */
public record ReviewModerationRequest(
    @NotBlank(message = "Lý do không được để trống")
    @Size(max = 500, message = "Lý do không được vượt quá 500 ký tự")
    String reason
) {
}


