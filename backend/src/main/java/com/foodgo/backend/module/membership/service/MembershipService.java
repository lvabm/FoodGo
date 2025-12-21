package com.foodgo.backend.module.membership.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.membership.dto.request.filter.MembershipPlanFilterRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import com.foodgo.backend.module.membership.dto.response.UserMembershipResponse;

public interface MembershipService
    extends ReadableService<Integer, MembershipPlanFilterRequest, MembershipPlanResponse> {

  // Đăng ký gói
  UserMembershipResponse subscribe(Integer planId);

  // Hủy gói hiện tại
  void cancelCurrentSubscription();
}
