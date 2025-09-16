package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fnb_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FnbSubCategory extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: FnbSubCategory <--> Fnb
  // fnb sở hữu quan hệ (fk_fnb_sub_category_id_fnb)
  @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Fnb> fnbs;

  // 2. QUAN HỆ MANY - TO - ONE: FnbCategory <--> FnbSubCategory
  // FnbSubCategory sở hữu quan hệ (fk_fnb_category_id_fnb_sub_category)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", nullable = false)
  private FnbCategory category;
}
