package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.review.dto.request.filter.ReviewReportFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewReportStatusUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewReportResponse;

public interface AdminReportService
    extends ReadableService<Integer, ReviewReportFilterRequest, ReviewReportResponse> {
  ReviewReportResponse updateStatus(Integer id, ReviewReportStatusUpdateRequest request);
}

