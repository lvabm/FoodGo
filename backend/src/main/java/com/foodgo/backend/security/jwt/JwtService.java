package com.foodgo.backend.security.jwt;

import com.foodgo.backend.module.user.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtService {
  String generateToken(UserAccount userAccount, Long refreshTokenId);

  String extractUsername(String token);

  UUID extractUserId(String token);

  String extractRoleName(String token);

  Long extractRefreshTokenId(String token);

  boolean isTokenValid(String token, UserDetails userDetails);
}
