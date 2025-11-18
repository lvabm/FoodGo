package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutletFnbHasFeatureCreateRequest {

    @NotNull(message = "ID tính năng F&B không được để trống")
    private Integer featureId;

    @NotNull(message = "ID món ăn của outlet không được để trống")
    private Integer outletFnbId;
}
