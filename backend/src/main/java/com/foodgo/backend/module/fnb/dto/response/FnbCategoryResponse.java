package com.foodgo.backend.module.fnb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FnbCategoryResponse {
    private Integer id;
    private String name;
    private String description;

    private Integer typeId;
    private Integer outletCategoryId;
}
