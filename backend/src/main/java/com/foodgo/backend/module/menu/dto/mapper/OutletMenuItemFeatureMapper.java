package com.foodgo.backend.module.menu.dto.mapper;

import com.foodgo.backend.common.base.mapper.CreatableMapper;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemFeatureCreateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletMenuItemFeatureMapper
    extends CreatableMapper<OutletMenuItemFeature, OutletMenuItemFeatureCreateRequest>,
        ReadableMapper<OutletMenuItemFeature, OutletMenuItemFeatureResponse> {

  // --- ReadableMapper (Entity -> Response) ---
  @Override
  @Mapping(target = "outletMenuItemId", source = "outletMenuItem.id")
  @Mapping(target = "featureId", source = "feature.id")
  @Mapping(target = "featureName", source = "feature.name")
  OutletMenuItemFeatureResponse toResponse(OutletMenuItemFeature entity);
}
