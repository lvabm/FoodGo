package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Integer> {
}
