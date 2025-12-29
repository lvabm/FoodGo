package com.foodgo.backend.module.location.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.location.dto.response.DistrictResponse;
import com.foodgo.backend.module.location.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistrictMapper extends BaseMapper<District, Object, Object, DistrictResponse> {

  @Override
  @Mapping(target = "provinceId", source = "province.id")
  @Mapping(target = "provinceName", source = "province.name") // Mapping từ quan hệ Province
  DistrictResponse toResponse(District entity);
}
