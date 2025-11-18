package com.foodgo.backend.module.fnb.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeatureOfFnbUpdateRequest {

    @Min(value = 1,message = "ID phải lớn hơn hoặc bằng 1")
    @NotNull(message = "ID không được để trống")
    private Integer id;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên category phải nhỏ hơn hoặc bằng 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả phải nhỏ hơn hoặc bằng 255 ký tự")
    private String description;

}
