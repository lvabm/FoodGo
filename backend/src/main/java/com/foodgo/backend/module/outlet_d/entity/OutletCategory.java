package com.foodgo.backend.module.outlet_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletCategory extends BaseEntity {
  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "type_id")
  private Integer typeId;

  @Column(name = "description", length = 255)
  private String description;
}
