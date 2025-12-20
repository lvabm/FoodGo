package com.foodgo.backend.module.auth.dto.mapper;

import com.foodgo.backend.module.auth.dto.RegisterRequest;
import com.foodgo.backend.module.user.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface AuthProfileMapper {

  Profile toEntity(RegisterRequest request);
}
