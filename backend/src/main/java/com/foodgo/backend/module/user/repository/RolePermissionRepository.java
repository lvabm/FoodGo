package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
