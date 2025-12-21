package com.foodgo.backend.module.membership.controller;

import com.foodgo.backend.module.membership.dto.request.filter.MembershipPlanFilterRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import com.foodgo.backend.module.membership.dto.response.UserMembershipResponse;
import com.foodgo.backend.module.membership.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership-plans")
public class MembershipController {

  private final MembershipService membershipService;

  @Operation(summary = "Lấy danh sách các gói membership (Có filter)")
  @GetMapping("/search")
  public Page<MembershipPlanResponse> getAll(
      @ParameterObject MembershipPlanFilterRequest filterRequest,
      @ParameterObject Pageable pageable) {
    return membershipService.getPage(filterRequest, pageable);
  }

  @Operation(summary = "Chi tiết một gói membership")
  @GetMapping("/{id}")
  public MembershipPlanResponse getById(@PathVariable Integer id) {
    return membershipService.getDetail(id);
  }

  @Operation(summary = "User/Owner đăng ký mua gói Membership")
  @PostMapping("/{id}/subscribe")
  public UserMembershipResponse subscribe(@PathVariable Integer id) {
    return membershipService.subscribe(id);
  }

  @Operation(summary = "User hủy gói Membership đang sử dụng")
  @PostMapping("/current/cancel")
  public void cancelCurrentSubscription() {
    membershipService.cancelCurrentSubscription();
  }
}
