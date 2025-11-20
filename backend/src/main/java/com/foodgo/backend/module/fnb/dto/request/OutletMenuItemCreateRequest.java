package com.foodgo.backend.module.fnb.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OutletMenuItemCreateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isAvailable;
    private UUID outletId;
    private UUID menuItemId;
}
