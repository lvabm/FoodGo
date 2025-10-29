package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_fnb_has_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletFnbHasFeature extends BaseEntity {
  @Column(name = "outlet_fnb_id", nullable = false)
  private Long outletFnbId;

  @Column(name = "feature_id", nullable = false)
  private Long featureId;
}
