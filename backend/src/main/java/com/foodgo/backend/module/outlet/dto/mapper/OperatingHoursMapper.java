package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OperatingHoursCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OperatingHoursUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OperatingHoursResponse;
import com.foodgo.backend.module.outlet.entity.OperatingHours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperatingHoursMapper
    extends BaseMapper<
        OperatingHours,
        OperatingHoursCreateRequest,
        OperatingHoursUpdateRequest,
        OperatingHoursResponse> {

  // --- ReadableMapper (Read) ---
  @Override
  @Mapping(target = "outletId", source = "outlet.id")
  @Mapping(target = "outletName", source = "outlet.name")
  OperatingHoursResponse toResponse(OperatingHours entity);
}
