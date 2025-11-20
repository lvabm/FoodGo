package com.foodgo.backend.module.fnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemFeatureUpdateRequest {
    private String name;
    private String featureType;
    private String valueType;
    private String possibleValues;
    private String description;
}
