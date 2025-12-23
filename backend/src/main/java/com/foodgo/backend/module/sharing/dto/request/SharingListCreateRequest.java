package com.foodgo.backend.module.sharing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SharingListCreateRequest {

  @NotNull(message = "User không được để trống")
  private Long userId;

  @NotNull(message = "Group không được để trống")
  private Long groupId;
}
