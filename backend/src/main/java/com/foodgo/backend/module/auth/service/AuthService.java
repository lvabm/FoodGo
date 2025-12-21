package com.foodgo.backend.module.auth.service;

import com.foodgo.backend.module.auth.dto.*;

public interface AuthService {

  // Đăng ký tài khoản (Tạo user + Tự động login/cấp token)
  AuthResponse register(RegisterRequest request);

  // Đăng nhập (trả access + refresh token)
  AuthResponse login(LoginRequest request);

  // Làm mới access token bằng refresh token
  AuthResponse refreshToken(RefreshTokenRequest request);

  // Đăng xuất (Hủy session)
  void logout();

  // Các method quên mật khẩu, verify email,..
}
