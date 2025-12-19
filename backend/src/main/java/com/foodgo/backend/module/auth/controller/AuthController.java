package com.foodgo.backend.module.auth.controller;

import com.foodgo.backend.module.auth.dto.*;
import com.foodgo.backend.module.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "Đăng ký tài khoản")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
    return authService.register(request);
  }

  @Operation(summary = "Đăng nhập")
  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @Operation(summary = "Làm mới Token")
  @PostMapping("/refresh")
  public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
    return authService.refreshToken(request);
  }

  @Operation(summary = "Đăng xuất")
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logout() {
    authService.logout();
  }
}
