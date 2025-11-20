package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import com.foodgo.backend.module.outlet.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "outlet_menu_item", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "outlet_id", "menu_item_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletMenuItem extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @Column(name = "is_available", nullable = false)
  @Builder.Default
  private Boolean isAvailable = true;

  // 1. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletMenuItem
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "outlet_id", nullable = false)
  private Outlet outlet;

  // 2. QUAN HỆ MANY - TO - ONE: MenuItem <--> OutletMenuItem
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_item_id", nullable = false)
  private MenuItem menuItem;
}
