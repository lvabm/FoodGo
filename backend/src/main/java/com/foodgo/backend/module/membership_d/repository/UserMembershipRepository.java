package com.foodgo.backend.module.membership_d.repository;

import com.foodgo.backend.module.membership_d.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {}
