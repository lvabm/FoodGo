package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminMembershipService;
import com.foodgo.backend.module.membership.dto.request.create.MembershipPlanCreateRequest;
import com.foodgo.backend.module.membership.dto.request.update.MembershipPlanUpdateRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/membership-plans")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMembershipController {

  private final AdminMembershipService adminMembershipService;

  @Operation(summary = "Tạo Membership Plan mới")
  @PostMapping
  public MembershipPlanResponse create(@Valid @RequestBody MembershipPlanCreateRequest request) {
    return adminMembershipService.create(request);
  }

  @Operation(summary = "Cập nhật Membership Plan")
  @PatchMapping("/{id}")
  public MembershipPlanResponse update(
      @PathVariable Integer id, @Valid @RequestBody MembershipPlanUpdateRequest request) {
    return adminMembershipService.update(id, request);
  }

  @Operation(summary = "Xóa mềm Membership Plan")
  @DeleteMapping("/{id}")
  public void softDelete(@PathVariable Integer id) {
    adminMembershipService.softDelete(id);
  }
}
