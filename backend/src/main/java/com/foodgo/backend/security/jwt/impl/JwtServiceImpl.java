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
  public String generateToken(UserAccount userAccount, Long refreshTokenId) {
    Instant now = Instant.now();
    String roleName = userAccount.getRole().getName();

    return Jwts.builder()
        .subject(userAccount.getUsername())
        .claim("id", userAccount.getId().toString())
        .claim("role", roleName)
        .claim("rtid", refreshTokenId) // 🔑 Quan trọng: Lưu ID session vào token
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
        .signWith(key)
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public UUID extractUserId(String token) {
    String idString = extractClaim(token, claims -> claims.get("id", String.class));
    if (idString == null) throw new IllegalArgumentException("JWT is missing 'id' claim.");
    return UUID.fromString(idString);
  }

  @Override
  public String extractRoleName(String token) {
    String roleName = extractClaim(token, claims -> claims.get("role", String.class));
    if (roleName == null) throw new IllegalArgumentException("JWT is missing 'role' claim.");
    return roleName;
  }

  // Trích xuất rtid
  @Override
  public Long extractRefreshTokenId(String token) {
    return extractClaim(token, claims -> claims.get("rtid", Long.class));
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
