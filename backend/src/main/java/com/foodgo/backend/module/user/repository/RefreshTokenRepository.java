package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {}
