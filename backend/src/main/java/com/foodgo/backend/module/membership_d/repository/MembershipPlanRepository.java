package com.foodgo.backend.module.membership_d.repository;

import com.foodgo.backend.module.membership_d.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {}
