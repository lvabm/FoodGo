package com.foodgo.backend.module.membership.dto.mapper;

import com.foodgo.backend.module.membership.dto.response.UserMembershipResponse;
import com.foodgo.backend.module.membership.entity.UserMembership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMembershipMapper {

  // Map dữ liệu từ bảng cha (MembershipPlan) sang DTO phẳng
  @Mapping(source = "membershipPlan.name", target = "planName")
  @Mapping(source = "membershipPlan.price", target = "pricePaid")
  UserMembershipResponse toResponse(UserMembership entity);
}
