package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.FeatureOfFnbCreateRequest;
import com.foodgo.backend.module.fnb.dto.response.FeatureOfFnbResponse;
import com.foodgo.backend.module.fnb.dto.request.FeatureOfFnbUpdateRequest;
import com.foodgo.backend.module.fnb.entity.FeatureOfFnb;
import org.springframework.stereotype.Component;

@Component
public class FeatureOfFnbMapper {
    public FeatureOfFnbResponse toResponseDTO(FeatureOfFnb entity) {
        if (entity == null) return null;

        FeatureOfFnbResponse dto = new FeatureOfFnbResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public FeatureOfFnb toEntity(FeatureOfFnbCreateRequest dto) {
        if (dto == null) return null;

        FeatureOfFnb entity = new FeatureOfFnb();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public void updateEntityFromDTO(FeatureOfFnb entity, FeatureOfFnbUpdateRequest dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}
