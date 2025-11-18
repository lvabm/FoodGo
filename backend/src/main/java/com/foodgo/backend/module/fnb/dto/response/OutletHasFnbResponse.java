package com.foodgo.backend.module.fnb.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OutletHasFnbResponse {
    private Integer id;
    private BigDecimal price;

    private UUID fnbId;

    private UUID outletId;
}
