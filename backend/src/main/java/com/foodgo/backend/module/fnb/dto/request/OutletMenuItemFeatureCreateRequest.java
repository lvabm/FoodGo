package com.foodgo.backend.module.fnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletMenuItemFeatureCreateRequest {
    private String value;
    private Integer outletMenuItemId;
    private Integer featureId;
}
