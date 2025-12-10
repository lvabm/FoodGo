package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureResponse;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletFeatureMapper extends ReadableMapper<OutletFeature, OutletFeatureResponse> {}
