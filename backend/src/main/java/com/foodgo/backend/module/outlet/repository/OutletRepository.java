package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OutletRepository extends JpaRepository<Outlet, UUID> {

  List<Outlet> findByOwnerId(UUID ownerId);

  List<Outlet> findByIsActiveTrue();
}
