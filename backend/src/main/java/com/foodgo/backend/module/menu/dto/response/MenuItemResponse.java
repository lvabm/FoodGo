package com.foodgo.backend.module.menu.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class MenuItemResponse {
  private UUID id;
  private String name;
  private String description;
  private Boolean isPopular;
  private Integer subCategoryId;
  private Integer provinceId;
}
