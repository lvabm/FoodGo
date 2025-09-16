package com.foodgo.backend.common.repository;

import com.foodgo.backend.common.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  boolean existsByName(String name);
}
