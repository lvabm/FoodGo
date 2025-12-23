package com.foodgo.backend.module.user.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.auth.dto.RegisterRequest;
import com.foodgo.backend.module.user.dto.response.UserAccountResponse;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAccountMapper
    extends BaseMapper<UserAccount, RegisterRequest, Object, UserAccountResponse> {

  @Mapping(target = "fullName", source = "profile.fullName")
  @Mapping(target = "address", source = "profile.address")
  @Mapping(target = "roleType", source = "role.name")
  @Mapping(target = "isActive", expression = "java(entity.isActive())")
  @Override
  UserAccountResponse toResponse(UserAccount entity);
}
