package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.service.BaseMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
// Kế thừa BaseMapper cho CRUD
public interface OutletMapper
    extends BaseMapper<Outlet, OutletRequest, OutletUpdateRequest, OutletResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "ownerId", source = "owner.id")
  @Mapping(target = "districtName", source = "district.name")
  @Mapping(target = "outletTypeName", source = "type.name")
  OutletResponse toResponse(Outlet entity);
}
