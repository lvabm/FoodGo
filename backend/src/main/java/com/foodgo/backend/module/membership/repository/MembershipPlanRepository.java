package com.foodgo.backend.module.membership.repository;

import com.foodgo.backend.module.membership.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {}
