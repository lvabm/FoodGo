package com.foodgo.backend.module.review.service;

import com.foodgo.backend.common.base.service.CreatableService;
import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.common.base.service.UpdatableService;
import com.foodgo.backend.common.constant.ReactionType;
import com.foodgo.backend.module.review.dto.request.create.ReviewCreateRequest;
import com.foodgo.backend.module.review.dto.request.filter.ReviewFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;

import java.util.UUID;

public interface ReviewService
    extends ReadableService<UUID, ReviewFilterRequest, ReviewResponse>,
        CreatableService<ReviewCreateRequest, ReviewResponse>,
        UpdatableService<UUID, ReviewUpdateRequest, ReviewResponse>,
        DeletableService<UUID, ReviewResponse> {
  void replyToReview(UUID reviewId, String replyText);

  void reactToReview(UUID reviewId, ReactionType type);

  ReviewResponse restore(UUID id);
}
