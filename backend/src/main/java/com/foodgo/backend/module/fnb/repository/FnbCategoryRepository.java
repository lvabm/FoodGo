package com.foodgo.backend.module.fnb.repository;

import com.foodgo.backend.module.fnb.entity.FnbCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FnbCategoryRepository extends JpaRepository<FnbCategory, Long> {
}
