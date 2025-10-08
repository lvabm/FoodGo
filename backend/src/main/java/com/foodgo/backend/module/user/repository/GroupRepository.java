package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
