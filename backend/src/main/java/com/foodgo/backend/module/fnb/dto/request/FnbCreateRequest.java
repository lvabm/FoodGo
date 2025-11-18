package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FnbCreateRequest {

    @NotBlank(message = "Tên món không được để trống")
    @Size(max = 255, message = "Tên món FNB phải nhỏ hơn hoặc bằng 255 ký tự")
    private String name;

    @NotNull(message = "Giá món không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá món phải lớn hơn 0")
    private BigDecimal price;

    @Size(max = 1000, message = "Mô tả phải nhỏ hơn hoặc bằng 1000 ký tự")
    private String description;

    @NotNull(message = "Vui lòng chọn danh mục con")
    private Integer subCategoryId;

    @NotNull(message = "Vui lòng chọn tỉnh/thành phố")
    private Integer provinceId;
}

