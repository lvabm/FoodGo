package com.foodgo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // Tắt CSRF cho H2
        .headers(
            headers -> headers.frameOptions(frame -> frame.disable()) // Cho phép iframe
            )
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/h2-console/**")
                    .permitAll() // Cho phép vào H2 Console
                    .anyRequest()
                    .permitAll());

    return http.build();
  }
}
