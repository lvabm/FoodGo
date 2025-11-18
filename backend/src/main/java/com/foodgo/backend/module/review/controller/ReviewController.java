package com.foodgo.backend.module.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

  @Operation(summary = "Xem danh sách quán đã đánh giá")
  @GetMapping("/users/{userId}")
  private void getUserReviews(@PathVariable Integer userId) {}

  @Operation(summary = "Hiển thị đánh giá tổng hợp")
  @GetMapping("/reviews-summary/outlets/{outletId}")
  private void getOutletReviewSummary(@PathVariable Integer outletId) {}

  @Operation(summary = "Xem danh sách đánh giá chi tiết")
  @GetMapping("/outlets/{outletId}")
  private void getOutletReviews(@PathVariable Integer outletId) {}
}
