package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
// Kế thừa BaseMapper cho CRUD
public interface OutletMapper
    extends BaseMapper<Outlet, OutletCreateRequest, OutletUpdateRequest, OutletResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "ownerId", source = "owner.id")
  @Mapping(target = "districtName", source = "district.name")
  @Mapping(target = "outletTypeName", source = "type.name")
  OutletResponse toResponse(Outlet entity);
}
