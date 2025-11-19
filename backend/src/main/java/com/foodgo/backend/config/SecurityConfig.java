package com.foodgo.backend.config;

import com.foodgo.backend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@ConditionalOnProperty(name = "app.security.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserDetailsService userDetailsService; // Đã khai báo

  // 1. Cấu hình Filter Chain chính
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // 1. Tắt CSRF (Bắt buộc cho ứng dụng API Stateless)
        .csrf(AbstractHttpConfigurer::disable)

        // 2. Quản lý Session (Bắt buộc cho JWT)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 3. Phân quyền Request (Authorization)
        .authorizeHttpRequests(
            auth ->
                auth
                    // API công khai: Đăng ký, Đăng nhập, và các endpoint Actuator
                    .requestMatchers("/api/auth/**", "/actuator/**")
                    .permitAll()
                    // Tất cả các request khác yêu cầu xác thực
                    .anyRequest()
                    .authenticated())

        // 4. Cấu hình Provider
        .authenticationProvider(authenticationProvider())

        // 5. Xử lý Ngoại lệ (Authentication/Access Denied)
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                          res.getWriter().write("Unauthorized: Token missing or invalid");
                        })
                    .accessDeniedHandler(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                          res.getWriter().write("Forbidden: Not enough privileges");
                        }))

        // 6. Thêm Filter JWT tùy chỉnh vào chuỗi
        // Đảm bảo JWT Filter chạy trước UsernamePasswordAuthenticationFilter
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // --- Các Beans cần thiết cho Authentication ---

  // 2. Cấu hình Authentication Provider
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService); // Sử dụng UserDetailsService đã inject
    authProvider.setPasswordEncoder(passwordEncoder()); // Sử dụng PasswordEncoder
    return authProvider;
  }

  // 3. Cấu hình Password Encoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    // BCrypt là chuẩn mực an toàn hiện nay
    return new BCryptPasswordEncoder();
  }
}
