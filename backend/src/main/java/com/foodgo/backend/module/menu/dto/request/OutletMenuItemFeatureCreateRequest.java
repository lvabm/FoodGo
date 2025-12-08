package com.foodgo.backend.module.menu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletMenuItemFeatureCreateRequest {
  private String value;
  private Integer outletMenuItemId;
  private Integer featureId;
}
