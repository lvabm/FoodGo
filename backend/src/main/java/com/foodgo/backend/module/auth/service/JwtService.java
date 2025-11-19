package com.foodgo.backend.module.auth.service;

import com.foodgo.backend.module.user.entity.UserAccount;

public interface JwtService {
  String generateToken(UserAccount userAccount);

  String extractUsername(String token);
}
