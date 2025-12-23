package com.foodgo.backend.module.review.dto.response;

import com.foodgo.backend.common.constant.ReportReason;
import com.foodgo.backend.common.constant.ReportStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewReportResponse(
    Integer id,
    UUID reviewId,
    String reviewSummary, // Trích đoạn nội dung review bị báo cáo
    String reporterName,
    ReportReason reason,
    String description,
    ReportStatus status,
    LocalDateTime createdAt) {}
