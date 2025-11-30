package com.foodgo.backend.module.membership.mapper;

import com.foodgo.backend.module.membership.dto.MembershipRequest;
import com.foodgo.backend.module.membership.dto.MembershipResponse;
import com.foodgo.backend.module.membership.entity.MembershipPlan;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface MembershipPlanMapper {

  MembershipPlan toEntity(MembershipRequest request);

  MembershipResponse toResponse(MembershipPlan plan);
}
