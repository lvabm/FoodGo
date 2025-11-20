package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {}
