package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.MenuItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemSubCategoryRepository extends JpaRepository<MenuItemSubCategory, Integer> {
}
