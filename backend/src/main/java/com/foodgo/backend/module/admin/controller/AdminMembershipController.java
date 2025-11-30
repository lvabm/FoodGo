package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.module.membership.dto.MembershipRequest;
import com.foodgo.backend.module.membership.dto.MembershipResponse;
import com.foodgo.backend.module.membership.service.MembershipService;
import com.foodgo.backend.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/memberships/plans")
public class AdminMembershipController {
  private final MembershipService membershipService;

  @Operation(summary = "Tạo membership plan mới")
  @PostMapping
  public ResponseEntity<BaseResponse<MembershipResponse>> createMembership(
      @Valid @RequestBody MembershipRequest request) {
    MembershipResponse data = membershipService.createMembership(request);
    return new ResponseEntity<>(
        ApiResponseBuilder.success("Tạo membership plan mới thành công", data), HttpStatus.CREATED);
  }

  @Operation(summary = "Lấy chi tiết membership plan theo ID")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<MembershipResponse>> getMembershipById(@PathVariable Long id) {
    MembershipResponse data = membershipService.getMembershipById(id);
    return ResponseEntity.ok(
        ApiResponseBuilder.success("Lấy chi tiết membership plan thành công", data));
  }
}
