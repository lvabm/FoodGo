package com.foodgo.backend.module.membership.repository;

import com.foodgo.backend.module.membership.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
}
