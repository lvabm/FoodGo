package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutletRepository extends JpaRepository<Outlet, String> {}
