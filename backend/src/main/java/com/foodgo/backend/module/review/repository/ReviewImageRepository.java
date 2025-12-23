package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
  void deleteAllByIdInAndReviewId(List<Long> ids, UUID reviewId);
}
