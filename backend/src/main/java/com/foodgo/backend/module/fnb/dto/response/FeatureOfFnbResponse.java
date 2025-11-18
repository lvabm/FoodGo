package com.foodgo.backend.module.fnb.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeatureOfFnbResponse {
    private Integer id;
    private String name;
    private String description;
}
