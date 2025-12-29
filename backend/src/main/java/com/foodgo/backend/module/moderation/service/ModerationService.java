package com.foodgo.backend.module.moderation.service;

import com.foodgo.backend.module.review.entity.Review;

/**
 * Service xử lý kiểm duyệt nội dung theo chuẩn nghiệp vụ
 */
public interface ModerationService {

  /**
   * Kiểm tra và tự động duyệt review nếu đáp ứng điều kiện
   * 
   * @param review Review cần kiểm tra
   * @return true nếu được tự động duyệt, false nếu cần admin xem xét
   */
  boolean autoModerateReview(Review review);

  /**
   * Kiểm tra nội dung có vi phạm quy định không
   * 
   * @param content Nội dung cần kiểm tra
   * @return true nếu vi phạm, false nếu hợp lệ
   */
  boolean containsViolation(String content);

  /**
   * Kiểm tra spam (nội dung trùng lặp, gửi quá nhiều)
   * 
   * @param review Review cần kiểm tra
   * @return true nếu là spam, false nếu hợp lệ
   */
  boolean isSpam(Review review);

  /**
   * Kiểm tra review có cần ẩn tự động không (dựa trên số lượng báo cáo)
   * 
   * @param review Review cần kiểm tra
   * @return true nếu cần ẩn, false nếu không
   */
  boolean shouldAutoHide(Review review);

  /**
   * Duyệt review (admin)
   * 
   * @param review Review cần duyệt
   * @param reason Lý do (nếu có)
   */
  void approveReview(Review review, String reason);

  /**
   * Từ chối review (admin)
   * 
   * @param review Review cần từ chối
   * @param reason Lý do từ chối
   */
  void rejectReview(Review review, String reason);

  /**
   * Ẩn review (admin hoặc tự động)
   * 
   * @param review Review cần ẩn
   * @param reason Lý do ẩn
   */
  void hideReview(Review review, String reason);
}

