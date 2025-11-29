package com.foodgo.backend.security;

import com.foodgo.backend.module.auth.service.JwtService;
import com.foodgo.backend.module.auth.service.impl.JpaUserDetailsServiceImpl;
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

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // 1. Lấy Header Authorization
    String authHeader = request.getHeader("Authorization");

    // 2. Kiểm tra và Bỏ qua nếu không có Bearer Token
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // 3. Trích xuất Token (Cắt bỏ "Bearer ")
    String token = authHeader.substring(7);

    try {
      // 4. Trích xuất Username từ Token
      String username = jwtService.extractUsername(token);

      // 5. Kiểm tra Username và Context hiện tại
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // 6. Tải thông tin User từ Database
        // userRepository là UserRepository hoặc UserService đã được inject
        var user = jpaUserDetailsServiceImpl.loadUserByUsername(username);

        if (user != null) {
          // Giả định JWT Service đã kiểm tra token hợp lệ trước đó (signature, expiry)

          // 7. Tạo đối tượng Xác thực (Authentication)
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  user, // Principal: đối tượng User (UserDetails)
                  null, // Credentials: luôn là null vì đã xác thực qua token
                  user.getAuthorities() // Authorities: quyền hạn của User
                  );

          // Thiết lập chi tiết xác thực (IP, Session ID...)
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          // 8. Thiết lập Context Bảo mật
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      // 🔑 Lỗi Token hết hạn
      System.err.println("JWT ERROR: Token hết hạn: " + e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Token EXPIRED");
      return; // Dừng chuỗi filter để trả về lỗi ngay
    } catch (io.jsonwebtoken.JwtException e) {
      // 🔑 Lỗi khác (Invalid Signature, v.v.)
      System.err.println("JWT ERROR: Token không hợp lệ: " + e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized: Token INVALID");
      return;
    }

    // 9. Tiếp tục chuỗi Filter
    filterChain.doFilter(request, response);
  }
}
