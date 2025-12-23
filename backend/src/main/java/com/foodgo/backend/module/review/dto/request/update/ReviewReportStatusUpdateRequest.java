package com.foodgo.backend.module.review.dto.request.update;

import com.foodgo.backend.common.constant.ReportStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewReportStatusUpdateRequest {
  @NotNull(message = "Trạng thái không được để trống")
  private ReportStatus status;
}

