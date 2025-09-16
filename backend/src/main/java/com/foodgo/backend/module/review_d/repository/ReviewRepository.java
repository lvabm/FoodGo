package com.foodgo.backend.module.review_d.repository;

import com.foodgo.backend.module.review_d.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}
