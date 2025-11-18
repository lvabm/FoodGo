package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.FnbSubCategoryCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.FnbSubCategoryUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.FnbSubCategoryResponse;
import com.foodgo.backend.module.fnb.entity.FnbCategory;
import com.foodgo.backend.module.fnb.entity.FnbSubCategory;
import org.springframework.stereotype.Component;

@Component
public class FnbSubCategoryMapper {
    public FnbSubCategoryResponse toResponseDTO(FnbSubCategory entity) {
        if (entity == null) return null;

        FnbSubCategoryResponse dto = new FnbSubCategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
        return dto;
    }

    public FnbSubCategory toEntity(FnbSubCategoryCreateRequest dto, FnbCategory category) {
        if (dto == null) return null;

        FnbSubCategory entity = new FnbSubCategory();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(category);
        return entity;
    }

    public void updateEntityFromDTO(FnbSubCategory entity, FnbSubCategoryUpdateRequest dto, FnbCategory category) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(category);
    }
}
