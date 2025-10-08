package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {}
