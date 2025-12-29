package com.foodgo.backend.module.review.dto.request.filter;

import com.foodgo.backend.common.constant.ReportStatus;
import java.util.Optional;
import java.util.UUID;

public record ReviewReportFilterRequest(ReportStatus status, UUID reviewId, UUID reporterId) {

  public Optional<ReportStatus> optionalStatus() {
    return Optional.ofNullable(status);
  }

  public Optional<UUID> optionalReviewId() {
    return Optional.ofNullable(reviewId);
  }

  public Optional<UUID> optionalReporterId() {
    return Optional.ofNullable(reporterId);
  }
}



