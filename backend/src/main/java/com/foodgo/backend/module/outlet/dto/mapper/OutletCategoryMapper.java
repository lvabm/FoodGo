package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletCategoryMapper
    extends BaseMapper<
        OutletCategory,
        com.foodgo.backend.module.outlet.dto.request.create.OutletCategoryCreateRequest,
        com.foodgo.backend.module.outlet.dto.request.update.OutletCategoryUpdateRequest,
        OutletCategoryResponse> {

  @Override
  @Mapping(target = "typeId", source = "type.id")
  @Mapping(target = "typeName", source = "type.name") // Mapping từ quan hệ OutletType
  OutletCategoryResponse toResponse(OutletCategory entity);

  // Ignore type trong toEntity - sẽ set thủ công trong Service
  @Override
  @Mapping(target = "type", ignore = true)
  OutletCategory toEntity(
      com.foodgo.backend.module.outlet.dto.request.create.OutletCategoryCreateRequest
          createRequest);

  // Ignore type trong updateEntity - sẽ set thủ công trong Service
  @Override
  @Mapping(target = "type", ignore = true)
  void updateEntity(
      com.foodgo.backend.module.outlet.dto.request.update.OutletCategoryUpdateRequest
          updateRequest,
      @org.mapstruct.MappingTarget OutletCategory entity);
}
