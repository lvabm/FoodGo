package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemSubCategoryRepository
    extends JpaRepository<MenuItemSubCategory, Integer>,
        JpaSpecificationExecutor<MenuItemSubCategory> {}
