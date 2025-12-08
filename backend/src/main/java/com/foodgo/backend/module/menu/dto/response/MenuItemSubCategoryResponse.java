package com.foodgo.backend.module.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemSubCategoryResponse {
  private Integer id;
  private String name;
  private String description;
  private Integer categoryId;
}
