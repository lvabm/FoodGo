package com.foodgo.backend.module.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletMenuItemFeatureResponse {
  private Integer id;
  private String value;
  private Integer outletMenuItemId;
  private Integer featureId;
}
