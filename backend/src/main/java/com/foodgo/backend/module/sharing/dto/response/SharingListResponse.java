package com.foodgo.backend.module.sharing.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class SharingListResponse {
  private Long id;
  private UUID userId;
  private Integer groupId;
}
