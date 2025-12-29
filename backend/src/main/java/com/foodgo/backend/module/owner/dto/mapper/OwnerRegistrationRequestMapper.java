package com.foodgo.backend.module.owner.dto.mapper;

import com.foodgo.backend.module.owner.dto.response.OwnerRegistrationRequestResponse;
import com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.user.dto.mapper.UserAccountMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserAccountMapper.class, OutletMapper.class})
public interface OwnerRegistrationRequestMapper {
  OwnerRegistrationRequestMapper INSTANCE = Mappers.getMapper(OwnerRegistrationRequestMapper.class);

  @Mapping(target = "user", source = "user")
  @Mapping(target = "outlet", source = "outlet")
  @Mapping(target = "reviewedBy", source = "reviewedBy")
  @Mapping(target = "createdAt", ignore = true) // Will be set manually if needed
  OwnerRegistrationRequestResponse toResponse(OwnerRegistrationRequest entity);
}

