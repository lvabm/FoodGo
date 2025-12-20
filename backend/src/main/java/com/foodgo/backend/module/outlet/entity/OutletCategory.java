package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletCategory extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ MANY - TO - ONE: OutletType <--> OutletCategory
  // OutletCategory sở hữu quan hệ (fk_outlet_type_id_outlet_category)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type_id", nullable = false)
  private OutletType type;
}
