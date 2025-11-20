package com.foodgo.backend.module.fnb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemFeatureResponse {
    private Integer id;
    private String name;
    private String featureType;
    private String valueType;
    private String possibleValues;
    private String description;
}
