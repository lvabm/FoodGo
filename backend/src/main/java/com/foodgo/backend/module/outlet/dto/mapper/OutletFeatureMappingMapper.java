package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.CreatableMapper;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletFeatureMappingMapper
    extends CreatableMapper<OutletFeatureMapping, OutletFeatureMappingCreateRequest>,
        ReadableMapper<OutletFeatureMapping, OutletFeatureMappingResponse> {

  @Override
  @Mapping(target = "outletId", source = "outlet.id")
  @Mapping(target = "featureId", source = "feature.id")
  @Mapping(target = "featureName", source = "feature.name")
  OutletFeatureMappingResponse toResponse(OutletFeatureMapping entity);
}
