package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
