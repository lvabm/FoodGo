package com.foodgo.backend.security.jwt.impl;

import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
  private final SecretKey key;
  private final long expMinutes;

  public JwtServiceImpl(
      @Value("${jwt.secret}") String base64Secret,
      @Value("${jwt.exp-minutes:60}") long expMinutes) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    this.expMinutes = expMinutes;
  }

  @Override
  public String generateToken(UserAccount userAccount) {
    Instant now = Instant.now();
    String roleName = userAccount.getRole().getName();

    return Jwts.builder()
        .subject(userAccount.getUsername())
        // 🔑 1. Thêm UUID ID (String)
        .claim("id", userAccount.getId().toString())
        // 🔑 2. Thêm tên Role (String)
        .claim("role", roleName)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
        .signWith(key)
        .compact();
  }

  // --- PHƯƠNG THỨC TRÍCH XUẤT CLAIMS (Bổ sung/Mở comment) ---

  // Hàm lõi: Trích xuất tất cả Claims sau khi xác minh chữ ký
  private Claims extractAllClaims(String token) {
    // Phân tích token, xác minh chữ ký bằng key, và lấy Payload (Claims)
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }

  // Hàm tiện ích: Trích xuất một Claim cụ thể
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String extractUsername(String token) {
    // Trích xuất username (subject)
    return extractClaim(token, Claims::getSubject);
  }

  // 🔑 BỔ SUNG: Trích xuất UUID ID
  @Override
  public UUID extractUserId(String token) {
    String idString = extractClaim(token, claims -> claims.get("id", String.class));
    if (idString == null) {
      throw new IllegalArgumentException("JWT is missing 'id' claim.");
    }
    return UUID.fromString(idString);
  }

  @Override
  public String extractRoleName(String token) {
    // Trích xuất Claim "role"
    String roleName = extractClaim(token, claims -> claims.get("role", String.class));

    if (roleName == null) {
      throw new IllegalArgumentException("JWT is missing 'role' claim.");
    }
    return roleName;
  }

  // --- PHƯƠNG THỨC KIỂM TRA TÍNH HỢP LỆ (Bổ sung/Mở comment) ---

  // 1. Trích xuất thời điểm hết hạn
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // 2. Kiểm tra token đã hết hạn chưa
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // 3. 🛡️ Hàm quan trọng: Kiểm tra tính hợp lệ của token
  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    // Kiểm tra: Username khớp VÀ Token chưa hết hạn
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
