package com.foodgo.backend.module.location.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.location.dto.response.ProvinceResponse;
import com.foodgo.backend.module.location.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper extends BaseMapper<Province, Object, Object, ProvinceResponse> {

  @Override
  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryName", source = "country.name")
  ProvinceResponse toResponse(Province entity);
}
