package com.foodgo.backend.module.fnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemCategoryCreateRequest {
    private String name;
    private String description;
    private Integer typeId;
}
