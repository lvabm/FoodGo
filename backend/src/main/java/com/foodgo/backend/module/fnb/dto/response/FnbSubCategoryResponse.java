package com.foodgo.backend.module.fnb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FnbSubCategoryResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
}
