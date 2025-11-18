package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.OutletFnbHasFeatureCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.OutletFnbHasFeatureUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.OutletFnbHasFeatureResponse;
import com.foodgo.backend.module.fnb.entity.FeatureOfFnb;
import com.foodgo.backend.module.fnb.entity.OutletFnbHasFeature;
import com.foodgo.backend.module.fnb.entity.OutletHasFnb;
import org.springframework.stereotype.Component;

@Component
public class OutletFnbHasFeatureMapper {
    public OutletFnbHasFeatureResponse toResponse(OutletFnbHasFeature entity) {
        if (entity == null) return null;

        OutletFnbHasFeatureResponse dto = new OutletFnbHasFeatureResponse();
        dto.setId(entity.getId());
        dto.setFeatureId(entity.getFeatureOfFnb().getId());
        dto.setOutletFnbId(entity.getOutletFnb().getId());
        return dto;
    }

    public OutletFnbHasFeature toEntity(
            OutletFnbHasFeatureCreateRequest dto,
            FeatureOfFnb feature,
            OutletHasFnb outletFnb
    ) {
        if (dto == null) return null;

        OutletFnbHasFeature entity = new OutletFnbHasFeature();
        entity.setFeatureOfFnb(feature);
        entity.setOutletFnb(outletFnb);
        return entity;
    }

    public void updateEntity(
            OutletFnbHasFeature entity,
            OutletFnbHasFeatureUpdateRequest dto,
            FeatureOfFnb feature,
            OutletHasFnb outletFnb
    ) {
        if (entity == null || dto == null) return;

        entity.setFeatureOfFnb(feature);
        entity.setOutletFnb(outletFnb);
    }
}
