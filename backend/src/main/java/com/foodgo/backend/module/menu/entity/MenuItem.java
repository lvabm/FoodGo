package com.foodgo.backend.module.menu.entity;

import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.module.location.entity.Province;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem extends BaseUUIDEntity {
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_popular", nullable = false)
  @Builder.Default
  private Boolean isPopular = false;

  // 1. QUAN HỆ MANY - TO - ONE: MenuItemSubCategory <--> MenuItem
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sub_category_id", nullable = false)
  private MenuItemSubCategory subCategory;

  // 2. QUAN HỆ MANY - TO - ONE: Province <--> MenuItem
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id", nullable = false)
  private Province province;

  // 3. QUAN HỆ ONE - TO - MANY: MenuItem <--> OutletMenuItem
  @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY)
  private List<OutletMenuItem> outletMenuItems;
}
