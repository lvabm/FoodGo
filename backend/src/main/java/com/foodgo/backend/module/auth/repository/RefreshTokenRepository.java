package com.foodgo.backend.module.auth.repository;

import com.foodgo.backend.module.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {}
