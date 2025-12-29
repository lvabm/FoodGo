package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.review.dto.response.ReviewResponse;

import java.util.UUID;

/**
 * Service quản lý kiểm duyệt nội dung cho Admin
 */
public interface AdminModerationService {

  /**
   * Duyệt review
   */
  ReviewResponse approveReview(UUID reviewId, String reason);

  /**
   * Từ chối review
   */
  ReviewResponse rejectReview(UUID reviewId, String reason);

  /**
   * Ẩn review
   */
  ReviewResponse hideReview(UUID reviewId, String reason);

  /**
   * Khôi phục review (từ HIDDEN/REJECTED về APPROVED)
   */
  ReviewResponse restoreReview(UUID reviewId);
}


