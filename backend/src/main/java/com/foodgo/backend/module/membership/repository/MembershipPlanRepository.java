package com.foodgo.backend.module.membership.repository;

import com.foodgo.backend.module.membership.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MembershipPlanRepository
    extends JpaRepository<MembershipPlan, Integer>, JpaSpecificationExecutor<MembershipPlan> {}
