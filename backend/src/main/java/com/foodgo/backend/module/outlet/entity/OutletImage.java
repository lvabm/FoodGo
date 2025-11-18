package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletImage extends BaseIntegerEntity<Integer> {

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletImage
  // OutletImage sở hữu quan hệ (fk_outlet_id_outlet_image)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;
}
