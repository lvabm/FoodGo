package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "outlet_has_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletHasFeature extends BaseEntity {
    @Column(name = "outlet_id", nullable = false)
    private UUID outletId;

    @Column(name = "feature_id", nullable = false)
    private Long featureId;
}
