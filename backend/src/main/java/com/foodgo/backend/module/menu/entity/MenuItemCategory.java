package com.foodgo.backend.module.menu.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "menu_item_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemCategory extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ MANY - TO - ONE: MenuItemType <--> MenuItemCategory
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type_id", nullable = false)
  private MenuItemType type;

  // 2. QUAN HỆ ONE - TO - MANY: MenuItemCategory <--> MenuItemSubCategory
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private Set<MenuItemSubCategory> menuItemSubCategories;
}
