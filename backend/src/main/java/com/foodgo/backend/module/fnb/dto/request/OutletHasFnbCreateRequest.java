package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutletHasFnbCreateRequest {

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Vui lòng chọn F&B")
    private Integer fnbId;

    @NotNull(message = "Vui lòng chọn outlet")
    private Integer outletId;
}
