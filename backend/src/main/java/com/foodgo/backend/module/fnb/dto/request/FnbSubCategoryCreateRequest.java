package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FnbSubCategoryCreateRequest {

    @NotBlank(message = "Tên sub-category không được để trống")
    @Size(max = 100, message = "Tên sub-category phải nhỏ hơn hoặc bằng 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả phải nhỏ hơn hoặc bằng 255 ký tự")
    private String description;

    @NotNull(message = "Vui lòng chọn category")
    private Integer categoryId;
}
