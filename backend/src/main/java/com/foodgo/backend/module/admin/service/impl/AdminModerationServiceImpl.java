package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.constant.ModerationStatus;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminModerationService;
import com.foodgo.backend.module.moderation.service.ModerationService;
import com.foodgo.backend.module.review.dto.mapper.ReviewMapper;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminModerationServiceImpl implements AdminModerationService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final ModerationService moderationService;

  @Override
  @Transactional
  public ReviewResponse approveReview(UUID reviewId, String reason) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Không tìm thấy đánh giá với ID: " + reviewId));

    moderationService.approveReview(review, reason);
    
    SuccessMessageContext.setMessage("Đã duyệt đánh giá thành công.");
    return reviewMapper.toResponse(review);
  }

  @Override
  @Transactional
  public ReviewResponse rejectReview(UUID reviewId, String reason) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Không tìm thấy đánh giá với ID: " + reviewId));

    if (reason == null || reason.isBlank()) {
      throw new IllegalArgumentException("Lý do từ chối không được để trống");
    }

    moderationService.rejectReview(review, reason);
    
    SuccessMessageContext.setMessage("Đã từ chối đánh giá thành công.");
    return reviewMapper.toResponse(review);
  }

  @Override
  @Transactional
  public ReviewResponse hideReview(UUID reviewId, String reason) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Không tìm thấy đánh giá với ID: " + reviewId));

    moderationService.hideReview(review, reason);
    
    SuccessMessageContext.setMessage("Đã ẩn đánh giá thành công.");
    return reviewMapper.toResponse(review);
  }

  @Override
  @Transactional
  public ReviewResponse restoreReview(UUID reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Không tìm thấy đánh giá với ID: " + reviewId));

    // Chỉ khôi phục nếu đang ở trạng thái HIDDEN hoặc REJECTED
    if (review.getModerationStatus() != ModerationStatus.HIDDEN &&
        review.getModerationStatus() != ModerationStatus.REJECTED) {
      throw new IllegalArgumentException(
          "Chỉ có thể khôi phục đánh giá đang ở trạng thái HIDDEN hoặc REJECTED");
    }

    moderationService.approveReview(review, "Đã khôi phục bởi admin");
    
    SuccessMessageContext.setMessage("Đã khôi phục đánh giá thành công.");
    return reviewMapper.toResponse(review);
  }
}


