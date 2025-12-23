package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import com.foodgo.backend.module.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
    name = "Admin | Review Management",
    description = "API Quản lý đánh giá - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/reviews")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {

  private final ReviewService reviewService;

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết đánh giá")
  public ReviewResponse getReviewDetail(@PathVariable UUID id) {
    return reviewService.getDetail(id);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Ẩn đánh giá (Soft Delete)")
  public ReviewResponse hideReview(@PathVariable UUID id) {
    return reviewService.softDelete(id);
  }

  @PatchMapping("/{id}/restore")
  @Operation(summary = "Hiển thị lại đánh giá (Restore)")
  public ReviewResponse restoreReview(@PathVariable UUID id) {
    return reviewService.restore(id);
  }
}

