package com.foodgo.backend.security;

import com.foodgo.backend.module.auth.service.JwtService;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
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
  private final UserAccountRepository userAccountRepository;

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
        var user = userAccountRepository.findByUsername(username).orElse(null);

        if (user != null) {
          // Giả định JWT Service đã kiểm tra token hợp lệ trước đó (signature, expiry)
          // Hoặc bạn có thể gọi jwtService.isTokenValid(token, user) ở đây

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

    } catch (Exception e) {
      // Log lỗi (ví dụ: Token hết hạn, chữ ký không hợp lệ)
      // System.out.println("JWT Exception: " + e.getMessage());
      // Cho phép Filter Chain tiếp tục, để Spring Security xử lý lỗi 401 Unauthorized sau.
    }

    // 9. Tiếp tục chuỗi Filter
    filterChain.doFilter(request, response);
  }
}
