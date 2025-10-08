package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
