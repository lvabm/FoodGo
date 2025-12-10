package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletCategoryMapper
    extends ReadableMapper<OutletCategory, OutletCategoryResponse> {

  @Override
  @Mapping(target = "typeId", source = "type.id")
  @Mapping(target = "typeName", source = "type.name") // Mapping từ quan hệ OutletType
  OutletCategoryResponse toResponse(OutletCategory entity);
}
