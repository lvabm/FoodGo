package com.foodgo.backend.module.user.mapper;

import com.foodgo.backend.module.admin.dto.user.UserAdminResponse;
import com.foodgo.backend.module.auth.dto.LoginRequest;
import com.foodgo.backend.module.auth.dto.RegisterRequest;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface UserAccountMapper {

  UserAccount toEntity(RegisterRequest request);

  UserAccount toEntity(LoginRequest request);

  @Mapping(target = "fullName", source = "profile.fullName")
  @Mapping(target = "address", source = "profile.address")
  @Mapping(target = "isActive", source = "active")
  @Mapping(target = "roleType", source = "role.name")
  UserAdminResponse toUserAdminDto(UserAccount userAccount);
}
