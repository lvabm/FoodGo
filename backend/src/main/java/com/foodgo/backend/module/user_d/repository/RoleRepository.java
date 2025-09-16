package com.foodgo.backend.module.user_d.repository;

import com.foodgo.backend.module.user_d.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
