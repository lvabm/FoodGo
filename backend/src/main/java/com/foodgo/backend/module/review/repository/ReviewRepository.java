package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
