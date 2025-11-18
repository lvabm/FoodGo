package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_has_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletHasFeature extends BaseIntegerEntity<Integer> {

  // 1. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletHasFeature
  // OutletHasFeature sở hữu quan hệ (fk_outlet_id_outlet_has_feature)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  // 2. QUAN HỆ MANY - TO - ONE: FeatureOfOutlet  <--> OutletHasFeature
  // OutletHasFeature sở hữu quan hệ (fk_feature_of_outlet_id_outlet_has_feature)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "feature_id", nullable = false)
  private FeatureOfOutlet featureOfOutlet;
}
