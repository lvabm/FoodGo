package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.FnbCreateRequest;
import com.foodgo.backend.module.fnb.dto.response.FnbResponse;
import com.foodgo.backend.module.fnb.dto.request.FnbUpdateRequest;
import com.foodgo.backend.module.fnb.entity.Fnb;
import com.foodgo.backend.module.fnb.entity.FnbSubCategory;
import com.foodgo.backend.module.location.entity.Province;
import org.springframework.stereotype.Component;

@Component
public class FnbMapper {
    public FnbResponse toResponseDTO(Fnb entity) {
        if (entity == null) return null;

        FnbResponse dto = new FnbResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());

        if (entity.getSubCategory() != null) {
            dto.setSubCategoryId(entity.getSubCategory().getId());
        }

        if (entity.getProvince() != null) {
            dto.setProvinceId(entity.getProvince().getId());
        }

        return dto;
    }

    public Fnb toEntity(FnbCreateRequest dto, FnbSubCategory subCategory, Province province) {
        if (dto == null) return null;

        Fnb entity = new Fnb();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setSubCategory(subCategory);
        entity.setProvince(province);

        return entity;
    }

    public void updateEntityFromDTO(Fnb entity, FnbUpdateRequest dto, FnbSubCategory subCategory, Province province) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setSubCategory(subCategory);
        entity.setProvince(province);
    }

}
