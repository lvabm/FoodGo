package com.foodgo.backend.module.menu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemFeatureCreateRequest {
  private String name;
  private String featureType;
  private String valueType;
  private String possibleValues;
  private String description;
}
