package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {
  Optional<ReviewReaction> findByReviewIdAndUserId(UUID reviewId, UUID userId);
}
