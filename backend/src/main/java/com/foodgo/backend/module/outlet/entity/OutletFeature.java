package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "outlet_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletFeature extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // QUAN HỆ ONE - TO - MANY: OutletFeature <--> OutletHasFeature
  @OneToMany(mappedBy = "feature", fetch = FetchType.LAZY)
  private List<OutletFeatureMapping> outletFeatureMappings;
}
