package com.foodgo.backend.module.fnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemUpdateRequest {
    private String name;
    private String description;
    private Boolean isPopular;
    private Integer subCategoryId;
    private Integer provinceId;
}
