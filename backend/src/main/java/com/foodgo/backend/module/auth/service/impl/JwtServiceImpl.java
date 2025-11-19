package com.foodgo.backend.module.auth.service.impl;

import com.foodgo.backend.module.auth.service.JwtService;
import com.foodgo.backend.module.user.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
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
    Instant now = Instant.now(); // Thời điểm hiện tại

    // Tối ưu hóa: Lấy tất cả quyền hạn và đưa vào claim "authorities"
    List<String> authorities =
        userAccount.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return Jwts.builder()
        // Thiết lập Subject (chủ thể) là username
        .subject(userAccount.getUsername())
        // 🔑 Tối ưu: Thêm danh sách quyền hạn
        .claim("authorities", authorities)
        // Thời điểm tạo token
        .issuedAt(Date.from(now))
        // Thời điểm hết hạn (thời điểm tạo + expMinutes * 60 giây)
        .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
        // Ký token bằng SecretKey
        .signWith(key)
        .compact(); // Nén thành chuỗi JWT
  }

  // Phương thức chung để trích xuất một claim cụ thể
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String extractUsername(String token) {
    // Trích xuất username (subject)
    return extractClaim(token, Claims::getSubject);
  }

  // 1. Trích xuất thời điểm hết hạn
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // 2. Kiểm tra token đã hết hạn chưa
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // 3. 🛡️ Hàm quan trọng: Kiểm tra tính hợp lệ của token
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    // Kiểm tra: Username khớp và Token chưa hết hạn
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
