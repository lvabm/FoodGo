package com.foodgo.backend.module.fnb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutletFnbHasFeatureResponse {
    private Integer id;
    private Integer featureId;
    private Integer outletFnbId;
}
