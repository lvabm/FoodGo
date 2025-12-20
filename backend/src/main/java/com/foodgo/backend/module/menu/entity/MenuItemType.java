package com.foodgo.backend.module.menu.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "menu_item_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemType extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ ONE - TO - MANY: MenuItemType <--> MenuItemCategory
  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  private Set<MenuItemCategory> menuItemCategories;
}
