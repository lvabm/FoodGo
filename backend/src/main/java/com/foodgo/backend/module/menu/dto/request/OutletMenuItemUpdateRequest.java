package com.foodgo.backend.module.menu.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OutletMenuItemUpdateRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private String imageUrl;
  private Boolean isAvailable;
}
