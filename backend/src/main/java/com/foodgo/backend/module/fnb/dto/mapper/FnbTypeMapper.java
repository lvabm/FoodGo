package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.FnbTypeCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.FnbTypeUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.FnbTypeResponse;
import com.foodgo.backend.module.fnb.entity.FnbType;
import org.springframework.stereotype.Component;

@Component
public class FnbTypeMapper {

    public FnbTypeResponse toResponseDTO(FnbType entity) {
        if (entity == null) return null;

        FnbTypeResponse dto = new FnbTypeResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public FnbType toEntity(FnbTypeCreateRequest dto) {
        if (dto == null) return null;

        FnbType entity = new FnbType();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public void updateEntity(FnbType entity, FnbTypeUpdateRequest dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}
