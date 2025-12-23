package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
