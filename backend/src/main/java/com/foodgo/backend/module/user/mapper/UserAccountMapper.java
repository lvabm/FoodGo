package com.foodgo.backend.module.user.mapper;

import com.foodgo.backend.module.auth.dto.LoginRequest;
import com.foodgo.backend.module.auth.dto.RegisterRequest;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface UserAccountMapper {

  UserAccount toEntity(RegisterRequest request);

  UserAccount toEntity(LoginRequest request);
}
