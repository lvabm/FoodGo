package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.membership.dto.MembershipRequest;
import com.foodgo.backend.module.membership.dto.MembershipResponse;
import com.foodgo.backend.module.membership.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/memberships/plans")
public class AdminMembershipController {
  private final MembershipService membershipService;

  @Operation(summary = "Tạo membership plan mới")
  @PostMapping
  public MembershipResponse createMembership(@Valid @RequestBody MembershipRequest request) {
    return membershipService.createMembership(request);
  }

  @Operation(summary = "Lấy chi tiết membership plan theo ID")
  @GetMapping("/{id}")
  public MembershipResponse getMembershipById(@PathVariable Long id) {
    return membershipService.getMembershipById(id);
  }
}
