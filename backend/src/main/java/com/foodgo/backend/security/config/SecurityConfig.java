package com.foodgo.backend.security.config;

import com.foodgo.backend.common.filter.ResponseHeaderFilter;
import com.foodgo.backend.security.filter.JwtAuthenticationFilter;
// Rate limiting đã được tắt - comment out import
// import com.foodgo.backend.security.filter.RateLimitFilter;
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

  // Rate limiting đã được tắt - comment out để tránh warning
  // private final RateLimitFilter rateLimitFilter;

  private final ResponseHeaderFilter responseHeaderFilter;

  private final UserDetailsService userDetailsService;

  // 1. Cấu hình Filter Chain chính
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // 1. Tắt CSRF (Bắt buộc cho ứng dụng API Stateless)
        .csrf(AbstractHttpConfigurer::disable)
        // 2. Security Headers - Bảo vệ khỏi các lỗ hổng bảo mật phổ biến
        .headers(headers -> headers
            .frameOptions(frame -> frame.deny()) // X-Frame-Options: DENY - Chống clickjacking
            .contentTypeOptions(contentType -> {}) // X-Content-Type-Options: nosniff
            .httpStrictTransportSecurity(hsts -> hsts
                .maxAgeInSeconds(31536000) // 1 year
            ) // HSTS - Force HTTPS (includeSubdomains is default in Spring Security 6.x)
            .referrerPolicy(referrer -> referrer.policy(org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
        )
        // 3. Quản lý Session (Bắt buộc cho JWT)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 4. Phân quyền Request (Authorization)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/v1/auth/**", // Đường dẫn hiện tại
                        "/api/v1/health/**", // Health check endpoint
                        "/v3/api-docs/**", // Đường dẫn tài liệu OpenAPI
                        "/swagger-ui/**", // Đường dẫn giao diện Swagger UI
                        "/swagger-ui.html", // File HTML chính
                        // Cho phép khách vãng lai xem menu và outlet details
                        "/api/v1/outlets/search", // Tìm kiếm outlets
                        "/api/v1/outlets/{id}", // Chi tiết outlet
                        "/api/v1/outlets/nearby", // Outlets gần đây
                        "/api/v1/outlets/{outletId}/menu-items/**", // Menu items của outlet
                        "/api/v1/outlets/{outletId}/menu-items", // Danh sách menu items
                        "/api/v1/outlets/{outletId}/menu-items/search", // Tìm kiếm menu items
                        "/api/v1/search/**", // Tìm kiếm nâng cao
                        "/api/v1/outlets/suggestions", // Gợi ý tìm kiếm
                        "/api/v1/outlets/autocomplete", // Autocomplete
                        "/api/v1/outlet-types/**", // Loại outlet
                        "/api/v1/outlet-categories/**", // Danh mục outlet
                        "/api/v1/outlet-features/**", // Tính năng outlet
                        "/api/v1/operating-hours/**", // Giờ hoạt động
                        // Menu endpoints - cho phép khách vãng lai xem
                        "/api/v1/menu-item-categories/**", // Danh mục món ăn
                        "/api/v1/menu-item-types/**", // Loại món ăn
                        "/api/v1/menu-item-sub-categories/**", // Danh mục phụ món ăn
                        "/api/v1/menu-item-features/**", // Tính năng món ăn
                        "/api/v1/menu-items/**", // Món ăn (master menu items) - GET only
                        "/api/v1/menu-items", // Danh sách món ăn
                        "/api/v1/menu-items/{id}", // Chi tiết món ăn (GET only)
                        // Reviews - cho phép khách vãng lai xem (GET only)
                        "/api/v1/reviews/search", // Tìm kiếm đánh giá
                        "/api/v1/reviews/outlet/{outletId}", // Đánh giá theo outlet
                        "/api/v1/reviews/{id}", // Chi tiết đánh giá (GET only)
                        // Statistics - cho phép khách vãng lai xem thống kê công khai
                        "/api/v1/outlets/newest", // Outlets mới nhất
                        "/api/v1/outlets/promotions", // Outlets đang khuyến mãi
                        "/api/v1/statistics/public" // Thống kê công khai (tổng số quán, món, đánh giá, người dùng)
                        )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        // 5. Cấu hình Provider
        .authenticationProvider(authenticationProvider())
        // 6. Xử lý Ngoại lệ (Authentication/Access Denied)
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
        // 7. Thêm Filters tùy chỉnh vào chuỗi
        // Order: RateLimitFilter -> ResponseHeaderFilter -> JwtAuthenticationFilter -> UsernamePasswordAuthenticationFilter
        // Note: Thêm JwtAuthenticationFilter trước, sau đó thêm các filter khác trước nó
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(responseHeaderFilter, JwtAuthenticationFilter.class);
        // Rate limiting đã được tắt - comment out dòng dưới để bật lại
        // .addFilterBefore(rateLimitFilter, ResponseHeaderFilter.class);

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
  @SuppressWarnings("deprecation") // RoleHierarchyImpl constructor và setHierarchy đều deprecated nhưng vẫn là cách duy nhất
  public RoleHierarchy roleHierarchy() {
    // Note: RoleHierarchyImpl constructor và setHierarchy đều deprecated trong Spring Security 6.x
    // nhưng vẫn là cách duy nhất để cấu hình role hierarchy hiện tại
    // Spring Security chưa cung cấp alternative API mới
    String hierarchy =
        "ROLE_SYSTEM_ADMIN > ROLE_ADMIN \n"
            + "ROLE_ADMIN > ROLE_OWNER \n"
            + "ROLE_OWNER > ROLE_USER \n"
            + "ROLE_USER > ROLE_GUEST";
    
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }
}
