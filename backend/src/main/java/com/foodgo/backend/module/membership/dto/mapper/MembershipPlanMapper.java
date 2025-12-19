package com.foodgo.backend.module.membership.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.membership.dto.request.create.MembershipPlanCreateRequest;
import com.foodgo.backend.module.membership.dto.request.update.MembershipPlanUpdateRequest;
import com.foodgo.backend.module.membership.dto.response.MembershipPlanResponse;
import com.foodgo.backend.module.membership.entity.MembershipPlan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MembershipPlanMapper
    extends BaseMapper<
        MembershipPlan,
        MembershipPlanCreateRequest,
        MembershipPlanUpdateRequest,
        MembershipPlanResponse> {}
