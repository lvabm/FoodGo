package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

  List<MenuItem> findByOutletId(UUID outletId);

  List<MenuItem> findByIsAvailableTrue();
}
