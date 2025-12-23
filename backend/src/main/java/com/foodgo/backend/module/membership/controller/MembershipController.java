package com.foodgo.backend.module.membership.controller;

import com.foodgo.backend.module.membership.dto.MembershipResponse;
import com.foodgo.backend.module.membership.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships/plans")
public class MembershipController {
  private final MembershipService membershipService;

  @Operation(summary = "Lấy danh sách membership plans")
  @GetMapping
  public List<MembershipResponse> getAllMemberships() {
    return membershipService.getAllMemberships();
  }
}
