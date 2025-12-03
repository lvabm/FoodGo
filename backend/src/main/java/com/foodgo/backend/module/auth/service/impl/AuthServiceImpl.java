package com.foodgo.backend.module.auth.service.impl;

import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.DataConflictException;
import com.foodgo.backend.module.auth.dto.*;
import com.foodgo.backend.module.auth.service.AuthService;
import com.foodgo.backend.security.jwt.JwtService;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.mapper.ProfileMapper;
import com.foodgo.backend.module.user.mapper.UserAccountMapper;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import com.foodgo.backend.common.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;

  private final UserAccountMapper userAccountMapper;
  private final ProfileMapper profileMapper;

  @Override
  @Transactional
  public AuthResponse register(RegisterRequest request) {
    if (userAccountRepository.existsByEmail(request.email())) {
      throw new DataConflictException("Email đã tồn tại");
    }

    if (!request.plainTextPassword().equals(request.passwordConfirmation())) {
      throw new BadRequestException("Mật khẩu xác nhận không trùng khớp");
    }

    var defaultRole =
        roleRepository
            .findByName(RoleType.ROLE_USER.getName())
            .orElseThrow(
                () -> new DataConflictException("Role mặc định (ROLE_USER) không tồn tại."));

    var userAccount = userAccountMapper.toEntity(request);
    var profile = profileMapper.toEntity(request);

    userAccount.setUsername(RandomUtils.generateUniqueUsername());
    userAccount.setPasswordHash(passwordEncoder.encode(request.plainTextPassword()));

    // Map 2 chiều (One to One)
    userAccount.setProfile(profile);
    userAccount.setRole(defaultRole);
    profile.setUserAccount(userAccount);

    var savedUser = userAccountRepository.save(userAccount);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.REGISTRATION_SUCCESS, savedUser.getId()));

    return generateAuthResponse(savedUser);
  }

  @Override
  @Transactional
  public AuthResponse login(LoginRequest request) {
    // 0. TÌM USER DÙNG @EntityGraph
    var user =
        userAccountRepository
            .findByEmail(request.email())
            .orElseThrow(
                () -> new DataConflictException("Email chưa có tài khoản hoặc không hợp lệ"));

    // 1. XÁC THỰC (Authentication)
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(), request.plainTextPassword()));

    // 2. LẤY USER
    var loginUser = (UserAccount) authentication.getPrincipal();

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.LOGIN_SUCCESSFUL, loginUser.getId()));

    return generateAuthResponse(loginUser);
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

  private AuthResponse generateAuthResponse(UserAccount user) {
    String accessToken = jwtService.generateToken(user);

    List<String> roles =
        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return new AuthResponse(accessToken, roles);
  }
}
