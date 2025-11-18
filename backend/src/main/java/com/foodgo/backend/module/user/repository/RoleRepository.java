package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
