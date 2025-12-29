package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminOwnerRegistrationService;
import com.foodgo.backend.module.owner.dto.response.OwnerRegistrationRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Admin | Owner Registration Management", description = "API Quản lý yêu cầu đăng ký owner - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/owner-registrations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOwnerRegistrationController {

  private final AdminOwnerRegistrationService adminOwnerRegistrationService;

  @GetMapping
  @Operation(summary = "Lấy tất cả yêu cầu đăng ký owner")
  public List<OwnerRegistrationRequestResponse> getAllRequests() {
    return adminOwnerRegistrationService.getAllRequests();
  }

  @GetMapping("/pending")
  @Operation(summary = "Lấy danh sách yêu cầu đang chờ duyệt")
  public List<OwnerRegistrationRequestResponse> getPendingRequests() {
    return adminOwnerRegistrationService.getAllPendingRequests();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Xem chi tiết yêu cầu đăng ký owner")
  public OwnerRegistrationRequestResponse getRequestDetail(@PathVariable UUID id) {
    return adminOwnerRegistrationService.getRequestDetail(id);
  }

  @PostMapping("/{id}/approve")
  @Operation(summary = "Duyệt yêu cầu đăng ký owner")
  public OwnerRegistrationRequestResponse approveRequest(
      @PathVariable UUID id,
      @RequestBody(required = false) String adminNotes) {
    return adminOwnerRegistrationService.approveRequest(id, adminNotes);
  }

  @PostMapping("/{id}/reject")
  @Operation(summary = "Từ chối yêu cầu đăng ký owner")
  @ResponseStatus(HttpStatus.OK)
  public OwnerRegistrationRequestResponse rejectRequest(
      @PathVariable UUID id,
      @RequestBody String adminNotes) {
    return adminOwnerRegistrationService.rejectRequest(id, adminNotes);
  }
}

