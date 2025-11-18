package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_fnb_has_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletFnbHasFeature extends BaseIntegerEntity<Integer> {

  // 2. QUAN HỆ MANY - TO - ONE: FeatureOfFnb <--> OutletFnbHasFeature
  // OutletFnbHasFeature sở hữu quan hệ (fk_feature_of_fnb_id_outlet_fnb_has_feature)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "feature_id", nullable = false)
  private FeatureOfFnb featureOfFnb;

  // 2. QUAN HỆ MANY - TO - ONE: OutletHasFnb <--> OutletFnbHasFeature
  // OutletFnbHasFeature sở hữu quan hệ (fk_outlet_has_fnb_id_outlet_fnb_has_feature)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_fnb_id", nullable = false)
  private OutletHasFnb outletFnb;
}
