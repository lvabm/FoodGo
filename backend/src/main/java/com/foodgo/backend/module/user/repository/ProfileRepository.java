package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
