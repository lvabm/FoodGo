package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OutletCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletCategoryRepository extends JpaRepository<OutletCategory, Long> {
}
