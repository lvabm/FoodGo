package com.foodgo.backend.module.outlet.mapper;

import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.module.outlet.dto.OutletTypeResponse;
import com.foodgo.backend.module.outlet.entity.OutletType;
import com.foodgo.backend.module.outlet.repository.projection.OutletTypeCountProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface OutletTypeMapper extends ReadableMapper<OutletType, OutletTypeResponse> {

  OutletTypeResponse toResponseFromProjection(OutletTypeCountProjection projection);

  List<OutletTypeResponse> toResponseListFromProjection(
      List<OutletTypeCountProjection> projections);
}
