package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.MenuItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Integer> {
}
