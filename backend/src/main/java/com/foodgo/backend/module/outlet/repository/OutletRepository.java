package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface OutletRepository
    extends JpaRepository<Outlet, UUID>, JpaSpecificationExecutor<Outlet> {
  
  Optional<Outlet> findByOwnerId(UUID ownerId);
}
