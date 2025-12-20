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
  ProfileResponse toResponse(Profile entity);
}
