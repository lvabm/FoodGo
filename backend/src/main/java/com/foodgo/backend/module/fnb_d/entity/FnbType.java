package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.outlet_d.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fnb_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FnbType extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  //1. QUAN HỆ ONE - TO - MANY: FnbType <--> FnbCategory
  // FnbCategory sở hữu quan hệ (fk_fnb_type_id_fnb_category)
  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<FnbCategory> fnbCategories;
}
