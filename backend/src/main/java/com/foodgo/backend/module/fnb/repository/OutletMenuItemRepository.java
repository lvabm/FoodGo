package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.OutletMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletMenuItemRepository extends JpaRepository<OutletMenuItem, Integer> {
}
