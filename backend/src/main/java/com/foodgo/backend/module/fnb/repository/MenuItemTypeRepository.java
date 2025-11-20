package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.MenuItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemTypeRepository extends JpaRepository<MenuItemType, Integer> {
}
