package com.foodgo.backend.module.auth.service;

import com.foodgo.backend.module.auth.dto.*;

public interface AuthService {

  // Đăng ký tài khoản (soft-create until verified)
  AuthResponse register(RegisterRequest request);

  // Đăng nhập (trả access + refresh token) — có thể rate-limit ở service hoặc filter
  AuthResponse login(LoginRequest request);

  // Làm mới access token từ refresh token
  AuthResponse refreshToken(RefreshTokenRequest request);

  // Đăng xuất (revoke refresh token)
  void logout(LogoutRequest request);

  // Gửi email đặt lại mật khẩu (throttle per email)
  void sendResetPasswordEmail(ForgotPasswordRequest request);

  // Gửi lại email đặt lại mật khẩu (throttle resend)
  void resendResetPasswordEmail(ForgotPasswordRequest request);

  // Đặt lại mật khẩu bằng token (validate token expiry)
  void resetPassword(ResetPasswordRequest request);

  // Đổi mật khẩu cho user đã đăng nhập (require current password)
  void changePassword(ChangePasswordRequest request);

  // Xác thực email bằng token (token truyền qua query)
  void verifyEmail(String token);

  // Gửi lại email xác thực (throttle resend)
  void resendVerificationEmail(ResendVerifyRequest request);
}
