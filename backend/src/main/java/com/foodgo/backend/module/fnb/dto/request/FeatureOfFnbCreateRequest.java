package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeatureOfFnbCreateRequest {

    @NotBlank(message = "Tên category không được để trống")
    @Size(max = 100, message = "Tên phải nhỏ hơn hoặc bằng 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả phải nhỏ hơn hoặc bằng 255 ký tự")
    private String description;
}
