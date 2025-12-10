package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutletMenuItemRepository
    extends JpaRepository<OutletMenuItem, Integer>, JpaSpecificationExecutor<OutletMenuItem> {

  // Phương thức cần thiết để kiểm tra trùng lặp
  boolean existsByOutletIdAndMenuItemId(UUID outletId, UUID menuItemId);
}
