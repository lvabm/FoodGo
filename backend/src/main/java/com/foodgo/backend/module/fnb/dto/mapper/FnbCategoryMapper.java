package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.FnbCategoryCreateRequest;
import com.foodgo.backend.module.fnb.dto.response.FnbCategoryResponse;
import com.foodgo.backend.module.fnb.dto.request.FnbCategoryUpdateRequest;
import com.foodgo.backend.module.fnb.entity.FnbCategory;
import com.foodgo.backend.module.fnb.entity.FnbType;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import org.springframework.stereotype.Component;

@Component
public class FnbCategoryMapper {
  public FnbCategoryResponse toResponseDTO(FnbCategory entity) {
    if (entity == null) return null;

    FnbCategoryResponse dto = new FnbCategoryResponse();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());

    if (entity.getType() != null) {
      dto.setTypeId(entity.getType().getId());
    }

    return dto;
  }

  public FnbCategory toEntity(FnbCategoryCreateRequest dto, FnbType type, OutletCategory category) {
    if (dto == null) return null;

    FnbCategory entity = new FnbCategory();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setType(type);

    return entity;
  }

  public void updateEntityFromDTO(
      FnbCategory entity, FnbCategoryUpdateRequest dto, FnbType type, OutletCategory category) {
    if (entity == null || dto == null) return;

    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setType(type);
  }
}
