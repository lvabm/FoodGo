package com.foodgo.backend.module.membership.repository;

import com.foodgo.backend.common.constant.PlanType;
import com.foodgo.backend.module.membership.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
  boolean existsByUserAccount_IdAndIsActiveTrueAndMembershipPlan_Type(UUID userId, PlanType type);
}
