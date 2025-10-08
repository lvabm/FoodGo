package com.foodgo.backend.module.review.repository;

import com.foodgo.backend.module.review.entity.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {
}
