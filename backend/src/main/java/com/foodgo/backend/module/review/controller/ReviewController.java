package com.foodgo.backend.module.review.controller;

import com.foodgo.backend.common.constant.ReactionType;
import com.foodgo.backend.module.review.dto.request.create.ReviewCreateRequest;
import com.foodgo.backend.module.review.dto.request.filter.ReviewFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import com.foodgo.backend.module.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "Quản lý Đánh giá & Phản hồi")
public class ReviewController {

  private final ReviewService service;

  @PostMapping
  @Operation(summary = "Viết đánh giá mới (Cần Booking Completed)")
  public ReviewResponse createReview(@RequestBody @Valid ReviewCreateRequest request) {
    return service.create(request);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Xem danh sách đánh giá (Lọc theo Quán/User/Rating)")
  public Page<ReviewResponse> getReviews(
      @ModelAttribute ReviewFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Sửa đánh giá (Trong vòng 24h)")
  public ReviewResponse updateReview(
      @PathVariable UUID id, @RequestBody ReviewUpdateRequest request) {
    return service.update(id, request);
  }

  @PostMapping("/{id}/reply")
  @Operation(summary = "Chủ quán trả lời đánh giá (1 lần duy nhất)")
  public void replyReview(
      @PathVariable UUID id, @RequestBody Map<String, String> payload) { // payload: {"text": "..."}
    service.replyToReview(id, payload.get("text"));
  }

  @PermitAll
  @PostMapping("/{id}/react")
  @Operation(summary = "Thả tim/Dislike (Toggle)")
  public void reactReview(@PathVariable UUID id, @RequestParam ReactionType type) {
    service.reactToReview(id, type);
  }
}
