package com.foodgo.backend.module.user.controller;

import com.foodgo.backend.module.user.dto.request.update.ProfileUpdateRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;
import com.foodgo.backend.module.user.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "User Profile",
    description = "API Quản lý thông tin cá nhân của User đang đăng nhập (Self-Managed).")
@RestController
@RequestMapping("/api/v1/profile/me")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  // 1. GET DETAIL (me)
  @GetMapping
  @Operation(summary = "Lấy Profile của User đang đăng nhập", description = "Tận dụng Facade.")
  public ProfileResponse getMyProfile() {
    return profileService.getMyProfile();
  }

  // 2. UPDATE (Dùng chung)
  @PatchMapping
  @Operation(
      summary = "Cập nhật Profile của chính mình (Partial Update)",
      description = "Service kiểm tra quyền Owner.")
  public ProfileResponse updateMyProfile(@Valid @RequestBody ProfileUpdateRequest request) {
    return profileService.updateMyProfile(request);
  }
}
