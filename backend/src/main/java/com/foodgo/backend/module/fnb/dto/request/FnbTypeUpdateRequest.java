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
public class FnbTypeUpdateRequest {

    @NotNull(message = "ID loại F&B là bắt buộc")
    private Integer id;

    @NotBlank(message = "Tên loại F&B không được để trống")
    @Size(max = 100, message = "Tên loại F&B không được vượt quá 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String description;
}
