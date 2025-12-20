package com.foodgo.backend.module.auth.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.module.auth.dto.*;
import com.foodgo.backend.module.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Operation(summary = "Làm mới token (refresh)")
  @PostMapping("/refresh")
  public ResponseEntity<BaseResponse<AuthResponse>> refresh(
      @Valid @RequestBody RefreshTokenRequest request) {
    AuthResponse auth = authService.refreshToken(request);
    BaseResponse<AuthResponse> body =
        BaseResponse.<AuthResponse>builder()
            .success(true)
            .message("Làm mới token thành công")
            .data(auth)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Đăng xuất (hủy token)")
  @PostMapping("/logout")
  public ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
    authService.logout(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đăng xuất thành công")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Quên mật khẩu (gửi email)")
  @PostMapping("/password/forgot")
  public ResponseEntity<BaseResponse<Void>> forgotPassword(
      @Valid @RequestBody ForgotPasswordRequest request) {
    authService.sendResetPasswordEmail(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đã gửi email khôi phục mật khẩu nếu email tồn tại")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Resend email đặt lại mật khẩu")
  @PostMapping("/password/forgot/resend")
  public ResponseEntity<BaseResponse<Void>> resendForgotPassword(
      @Valid @RequestBody ForgotPasswordRequest request) {
    authService.resendResetPasswordEmail(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đã gửi lại email khôi phục")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Đặt lại mật khẩu")
  @PostMapping("/password/reset")
  public ResponseEntity<BaseResponse<Void>> resetPassword(
      @Valid @RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đặt lại mật khẩu thành công")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Đổi mật khẩu (authenticated)")
  @PostMapping("/password/change")
  public ResponseEntity<BaseResponse<Void>> changePassword(
      @Valid @RequestBody ChangePasswordRequest request) {
    authService.changePassword(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đổi mật khẩu thành công")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Xác thực email")
  @GetMapping("/verify")
  public ResponseEntity<BaseResponse<Void>> verifyEmail(@RequestParam("token") String token) {
    authService.verifyEmail(token);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Xác thực email thành công")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Resend xác thực email")
  @PostMapping("/verify/resend")
  public ResponseEntity<BaseResponse<Void>> resendVerifyEmail(
      @Valid @RequestBody ResendVerifyRequest request) {
    authService.resendVerificationEmail(request);
    BaseResponse<Void> body =
        BaseResponse.<Void>builder()
            .success(true)
            .message("Đã gửi lại email xác thực")
            .data(null)
            .build();
    return ResponseEntity.ok(body);
  }
}
