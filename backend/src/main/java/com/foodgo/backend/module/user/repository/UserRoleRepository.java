package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
