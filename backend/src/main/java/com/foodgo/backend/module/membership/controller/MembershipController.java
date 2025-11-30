package com.foodgo.backend.module.membership.controller;

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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships/plans")
public class MembershipController {
  private final MembershipService membershipService;

  @Operation(summary = "Lấy danh sách membership plans")
  @GetMapping
  public ResponseEntity<BaseResponse<List<MembershipResponse>>> getAllMemberships() {
    List<MembershipResponse> data = membershipService.getAllMemberships();
    return ResponseEntity.ok(
        ApiResponseBuilder.success("Lấy danh sách tất cả membership plan thành công", data));
  }
}
