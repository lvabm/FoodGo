package com.foodgo.backend.module.fnb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FnbResponse {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;

    private Integer subCategoryId;
    private Integer provinceId;

}
