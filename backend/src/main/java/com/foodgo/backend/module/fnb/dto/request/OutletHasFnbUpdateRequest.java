package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutletHasFnbUpdateRequest {

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    // ở đây DTO này ko có các trường còn lại là
    // fnbId và outletId lí do để bảo toàn dữ liệu tránh sai lệch,
    // vì OutletHasFnb nghĩa là outlet đã gắn với 1 fnb, ko thể để fnb đó update thành 1 món khác
    // tương tự với outlet hợp lí khi update để món đó đổi sang outlet khác.
}
