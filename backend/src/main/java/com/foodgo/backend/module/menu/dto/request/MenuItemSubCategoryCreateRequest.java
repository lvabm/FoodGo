package com.foodgo.backend.module.menu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemSubCategoryCreateRequest {
  private String name;
  private String description;
  private Integer categoryId;
}
