package com.foodgo.backend.module.sharing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SharingListUpdateRequest {
  @NotNull(message = "ID không được để trống")
  private Long id;

  @NotNull(message = "Vui lòng chọn user để trống")
  private Long userId;

  @NotNull(message = "Vui lòng chọn group để trống")
  private Long groupId;
}
