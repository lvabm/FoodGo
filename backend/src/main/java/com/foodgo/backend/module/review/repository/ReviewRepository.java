package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository
    extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {
  boolean existsByBookingId(UUID bookingId);
  
  List<Review> findByOutletIdIn(List<UUID> outletIds);
  
  long countByUserIdAndCreateAtAfter(UUID userId, java.time.LocalDateTime after);
}
