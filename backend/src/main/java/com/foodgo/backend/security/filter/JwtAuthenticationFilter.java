package com.foodgo.backend.security.filter;

import com.foodgo.backend.module.auth.repository.RefreshTokenRepository;
import com.foodgo.backend.security.jwt.JwtService;
import com.foodgo.backend.security.jwt.impl.JpaUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final JpaUserDetailsServiceImpl jpaUserDetailsServiceImpl;
  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);

    try {
      String username = jwtService.extractUsername(token);
      // 🔑 Mới: Lấy rtid từ token
      Long rtid = jwtService.extractRefreshTokenId(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // 🛡️ CHECK NGHIÊM NGẶT: Kiểm tra Session trong DB
        var storedRefreshToken = refreshTokenRepository.findById(rtid).orElse(null);

        // Access token chỉ hợp lệ nếu Refresh token gốc:
        // 1. Tồn tại trong DB
        // 2. Chưa bị revoke (isRevoked = false)
        // 3. Chưa hết hạn (expiresAt > now)
        if (storedRefreshToken == null
            || storedRefreshToken.isRevoked()
            || storedRefreshToken.getExpiresAt().isBefore(java.time.Instant.now())) {

          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Unauthorized: Session Revoked or Expired");
          return;
        }

        var user = jpaUserDetailsServiceImpl.loadUserByUsername(username);

        if (user != null && jwtService.isTokenValid(token, user)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Token EXPIRED");
      return;
    } catch (Exception e) { // Catch all JWT errors
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Token INVALID");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
