package com.foodgo.backend.module.auth_d.repository;

import com.foodgo.backend.module.auth_d.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {}
