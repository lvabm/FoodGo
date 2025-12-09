package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "outlet_feature_mapping",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"outlet_id", "feature_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletFeatureMapping extends BaseIntegerEntity<Integer> {

  // 1. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletFeatureMapping
  // OutletFeatureMapping sở hữu quan hệ (fk_outlet_id_outlet_feature_mapping)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  // 2. QUAN HỆ MANY - TO - ONE: OutletFeature <--> OutletFeatureMapping
  // OutletFeatureMapping sở hữu quan hệ (fk_feature_id_outlet_feature_mapping)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feature_id", nullable = false)
  private OutletFeature feature;
}
