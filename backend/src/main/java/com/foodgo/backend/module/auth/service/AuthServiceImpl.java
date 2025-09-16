package com.foodgo.backend.module.auth.service;

import com.foodgo.backend.module.auth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  @Override
  public AuthResponse register(RegisterRequest request) {
    // TODO: Kiểm tra tồn tại user, lưu tạm, gửi email xác thực
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public AuthResponse login(LoginRequest request) {
    // TODO: Kiểm tra credential, sinh token
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public AuthResponse refreshToken(RefreshTokenRequest request) {
    // TODO: Validate refresh token, cấp token mới
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void logout(LogoutRequest request) {
    // TODO: Xóa refresh token khỏi DB
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void sendResetPasswordEmail(ForgotPasswordRequest request) {
    // TODO: Gửi email reset password
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void resendResetPasswordEmail(ForgotPasswordRequest request) {
    // TODO: Gửi lại email reset password
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void resetPassword(ResetPasswordRequest request) {
    // TODO: Kiểm tra token hợp lệ, đổi mật khẩu
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void changePassword(ChangePasswordRequest request) {
    // TODO: Kiểm tra mật khẩu hiện tại, đổi mật khẩu mới
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void verifyEmail(String token) {
    // TODO: Xác thực token và mark verified
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void resendVerificationEmail(ResendVerifyRequest request) {
    // TODO: Gửi lại email verify
    throw new UnsupportedOperationException("Not implemented");
  }
}
