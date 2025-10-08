package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "outlet_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletImage extends BaseEntity {
  @Column(name = "outlet_id", nullable = false)
  private UUID outletId;

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;

  @Column(name = "description", length = 255)
  private String description;
}
