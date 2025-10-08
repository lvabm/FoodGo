package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {
}
