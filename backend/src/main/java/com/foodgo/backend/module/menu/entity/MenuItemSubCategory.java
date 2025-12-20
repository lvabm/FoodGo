package com.foodgo.backend.module.menu.entity;

import com.foodgo.backend.common.base.dto.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "menu_item_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemSubCategory extends BaseIntegerEntity<Integer> {
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  // 1. QUAN HỆ MANY - TO - ONE: MenuItemCategory <--> MenuItemSubCategory
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private MenuItemCategory category;

  // 2. QUAN HỆ ONE - TO - MANY: MenuItemSubCategory <--> MenuItem
  @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
  private Set<MenuItem> menuItems;
}
