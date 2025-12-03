package com.foodgo.backend.security.jwt;

import com.foodgo.backend.module.user.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtService {
  String generateToken(UserAccount userAccount);

  String extractUsername(String token);

  // 🔑 BỔ SUNG: Trích xuất UUID ID
  UUID extractUserId(String token);

  String extractRoleName(String token);

  // 3. 🛡️ Hàm quan trọng: Kiểm tra tính hợp lệ của token
  boolean isTokenValid(String token, UserDetails userDetails);
}
