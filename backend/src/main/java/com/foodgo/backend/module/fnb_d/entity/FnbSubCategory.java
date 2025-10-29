package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fnb_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FnbSubCategory extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "description", length = 255)
  private String description;
}
