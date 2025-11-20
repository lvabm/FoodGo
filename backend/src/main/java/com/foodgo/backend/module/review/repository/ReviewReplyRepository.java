package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Integer> {
}
