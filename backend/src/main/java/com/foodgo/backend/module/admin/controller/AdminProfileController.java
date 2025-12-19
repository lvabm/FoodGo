package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminProfileService;
import com.foodgo.backend.module.user.dto.request.filter.ProfileFilterRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Admin | User Profile",
    description = "API Đọc dữ liệu Profile trên toàn hệ thống (Chỉ Admin).")
@RestController
@RequestMapping("/api/v1/admin/profiles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProfileController {

  private final AdminProfileService adminProfileService;

  // 1. GET ALL / SEARCH (Admin có thể tìm kiếm và phân trang tất cả)
  @GetMapping("/search")
  @Operation(
      summary = "Tìm kiếm và Phân trang tất cả Profile (Admin)",
      description = "Sử dụng Specification để lọc Profile theo tên, ID User, Quốc gia, v.v.")
  public Page<ProfileResponse> searchProfiles(
      @ModelAttribute ProfileFilterRequest filter, Pageable pageable) {
    return adminProfileService.getPage(filter, pageable);
  }

  // 2. GET DETAIL (Admin có thể xem chi tiết bất kỳ Profile nào)
  @GetMapping("/{id}")
  @Operation(
      summary = "Lấy chi tiết Profile theo ID (Admin)",
      description = "Lấy thông tin của bất kỳ Profile nào bằng ID Profile.")
  public ProfileResponse getDetailProfile(@PathVariable Long id) {
    return adminProfileService.getDetail(id);
  }
}
