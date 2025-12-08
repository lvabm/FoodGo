package com.foodgo.backend.module.location.mapper;

import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.module.location.dto.ProvinceResponse;
import com.foodgo.backend.module.location.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper extends ReadableMapper<Province, ProvinceResponse> {

  @Override
  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryName", source = "country.name")
  ProvinceResponse toResponse(Province entity);
}
