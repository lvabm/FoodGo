package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_has_fnb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletHasFnb extends BaseEntity {
    @Column(name = "outlet_id", nullable = false)
    private java.util.UUID outletId;

    @Column(name = "fnb_id", nullable = false)
    private Long fnbId;

    @Column(name = "price")
    private java.math.BigDecimal price;
}
