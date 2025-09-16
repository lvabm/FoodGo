package com.foodgo.backend.module.user_d.repository;

import com.foodgo.backend.module.user_d.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {}
