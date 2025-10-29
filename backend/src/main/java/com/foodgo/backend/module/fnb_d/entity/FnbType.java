package com.foodgo.backend.module.fnb_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
}
