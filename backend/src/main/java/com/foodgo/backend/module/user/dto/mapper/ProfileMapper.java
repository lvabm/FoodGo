package com.foodgo.backend.module.user.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.user.dto.request.update.ProfileUpdateRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;
import com.foodgo.backend.module.user.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper
    extends BaseMapper<Profile, Object, ProfileUpdateRequest, ProfileResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "userId", source = "userAccount.id")
  @Mapping(target = "countryName", source = "country.name")
  @Mapping(target = "membershipName", expression = "java(findActiveMembershipPlanName(entity))")
  @Mapping(target = "membershipStartDate", expression = "java(findActiveMembershipStartDate(entity))")
  @Mapping(target = "membershipEndDate", expression = "java(findActiveMembershipEndDate(entity))")
  @Mapping(target = "membershipIsActive", expression = "java(findActiveMembershipIsActive(entity))")
  ProfileResponse toResponse(Profile entity);

  // Helper methods to extract active membership info safely
  default String findActiveMembershipPlanName(Profile entity) {
    if (entity == null || entity.getUserAccount() == null || entity.getUserAccount().getUserMemberships() == null) return null;
    return entity.getUserAccount()
        .getUserMemberships()
        .stream()
        .filter(um -> Boolean.TRUE.equals(um.getIsActive()))
        .findFirst()
        .map(um -> um.getMembershipPlan().getName())
        .orElse(null);
  }

  default java.time.LocalDate findActiveMembershipStartDate(Profile entity) {
    if (entity == null || entity.getUserAccount() == null || entity.getUserAccount().getUserMemberships() == null) return null;
    return entity.getUserAccount()
        .getUserMemberships()
        .stream()
        .filter(um -> Boolean.TRUE.equals(um.getIsActive()))
        .findFirst()
        .map(um -> um.getStartDate())
        .orElse(null);
  }

  default java.time.LocalDate findActiveMembershipEndDate(Profile entity) {
    if (entity == null || entity.getUserAccount() == null || entity.getUserAccount().getUserMemberships() == null) return null;
    return entity.getUserAccount()
        .getUserMemberships()
        .stream()
        .filter(um -> Boolean.TRUE.equals(um.getIsActive()))
        .findFirst()
        .map(um -> um.getEndDate())
        .orElse(null);
  }

  default Boolean findActiveMembershipIsActive(Profile entity) {
    if (entity == null || entity.getUserAccount() == null || entity.getUserAccount().getUserMemberships() == null) return null;
    return entity.getUserAccount()
        .getUserMemberships()
        .stream()
        .filter(um -> Boolean.TRUE.equals(um.getIsActive()))
        .findFirst()
        .map(um -> um.getIsActive())
        .orElse(null);
  }
}
