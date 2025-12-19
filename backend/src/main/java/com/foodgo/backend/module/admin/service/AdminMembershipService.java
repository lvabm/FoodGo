package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.CreatableService;
import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.UpdatableService;
import com.foodgo.backend.module.membership.dto.request.create.MembershipPlanCreateRequest;
import com.foodgo.backend.module.membership.dto.request.update.MembershipPlanUpdateRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import com.foodgo.backend.module.membership.entity.MembershipPlan;

public interface AdminMembershipService
    extends CreatableService<MembershipPlanCreateRequest, MembershipPlanResponse>,
        UpdatableService<Integer, MembershipPlanUpdateRequest, MembershipPlanResponse>,
        DeletableService<Integer, MembershipPlanResponse> {}
