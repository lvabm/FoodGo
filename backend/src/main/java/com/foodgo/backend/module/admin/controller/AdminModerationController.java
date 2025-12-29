package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminModerationService;
import com.foodgo.backend.module.review.dto.request.moderation.ReviewModerationRequest;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller quản lý kiểm duyệt nội dung - Chỉ Admin
 */
@Tag(name = "Admin | Moderation Management", description = "API Quản lý kiểm duyệt - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/moderation")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminModerationController {

  private final AdminModerationService adminModerationService;

  @PostMapping("/reviews/{id}/approve")
  @Operation(summary = "Duyệt review")
  public ReviewResponse approveReview(
      @PathVariable UUID id,
      @RequestBody(required = false) @Valid ReviewModerationRequest request) {
    return adminModerationService.approveReview(id, request != null ? request.reason() : null);
  }

  @PostMapping("/reviews/{id}/reject")
  @Operation(summary = "Từ chối review")
  public ReviewResponse rejectReview(
      @PathVariable UUID id,
      @RequestBody @Valid ReviewModerationRequest request) {
    return adminModerationService.rejectReview(id, request.reason());
  }

  @PostMapping("/reviews/{id}/hide")
  @Operation(summary = "Ẩn review")
  public ReviewResponse hideReview(
      @PathVariable UUID id,
      @RequestBody(required = false) @Valid ReviewModerationRequest request) {
    return adminModerationService.hideReview(id, request != null ? request.reason() : null);
  }

  @PostMapping("/reviews/{id}/restore")
  @Operation(summary = "Khôi phục review (từ HIDDEN/REJECTED về APPROVED)")
  public ReviewResponse restoreReview(@PathVariable UUID id) {
    return adminModerationService.restoreReview(id);
  }
}


