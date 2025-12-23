package com.foodgo.backend.security.config;

import com.foodgo.backend.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  private final UserDetailsService userDetailsService;

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
                auth.requestMatchers(
                        "/api/v1/auth/**", // Đường dẫn hiện tại
                        "/v3/api-docs/**", // Đường dẫn tài liệu OpenAPI
                        "/swagger-ui/**", // Đường dẫn giao diện Swagger UI
                        "/swagger-ui.html", // File HTML chính
                        // Public APIs - cho phép khách vãng lai xem
                        // Note: Các controller methods có @PermitAll sẽ override SecurityConfig
                        "/api/v1/outlets/search", // Tìm kiếm outlets
                        "/api/v1/outlets/*", // Chi tiết outlet (GET)
                        "/api/v1/outlets/*/menu-items", // Menu items của outlet
                        "/api/v1/outlets/*/menu-items/search", // Search menu items
                        "/api/v1/menu-items", // Tìm kiếm menu items
                        "/api/v1/reviews/search", // Tìm kiếm reviews
                        "/api/v1/reviews/*/react", // React to review
                        "/api/v1/outlet-types/**", // Outlet types
                        "/api/v1/outlet-categories/**", // Outlet categories
                        "/api/v1/outlet-features/**", // Outlet features
                        "/api/v1/menu-item-categories/**", // Menu categories
                        "/api/v1/menu-item-types/**", // Menu types
                        "/api/v1/menu-item-sub-categories/**", // Menu sub categories
                        "/api/v1/menu-item-features/**", // Menu features
                        "/api/v1/countries/**", // Countries
                        "/api/v1/provinces/**", // Provinces
                        "/api/v1/districts/**" // Districts
                        )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        // 4. Cấu hình Provider
        .authenticationProvider(authenticationProvider())
        // 5. Xử lý Ngoại lệ (Authentication/Access Denied)
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                          res.getWriter().write("Unauthorized: Token missing or invalid");
                        })
                    .accessDeniedHandler(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                          res.getWriter().write("Forbidden: Not enough privileges");
                        }))
        // 6. Thêm Filter JWT tùy chỉnh vào chuỗi
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // 2. Cấu hình Authentication Provider
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  // 3. Cấu hình Password Encoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationProvider authenticationProvider) {
    // Dùng ProviderManager để quản lý AuthenticationProvider đã cấu hình
    return new ProviderManager(Collections.singletonList(authenticationProvider));
  }

  // 4. Cấu hình Role Hierarchy
  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

    // Đảm bảo chuỗi phân cấp phản ánh chính xác mô hình của bạn
    String hierarchy =
        "ROLE_SYSTEM_ADMIN > ROLE_ADMIN \n"
            + "ROLE_ADMIN > ROLE_OWNER \n"
            + "ROLE_OWNER > ROLE_USER \n"
            + "ROLE_USER > ROLE_GUEST";

    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }
}
