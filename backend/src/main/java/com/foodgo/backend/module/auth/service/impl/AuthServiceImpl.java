package com.foodgo.backend.module.auth.service.impl;

import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.DataConflictException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.common.exception.UnauthorizedException;
import com.foodgo.backend.module.auth.dto.*;
import com.foodgo.backend.module.auth.entity.RefreshToken;
import com.foodgo.backend.module.auth.repository.RefreshTokenRepository;
import com.foodgo.backend.module.auth.service.AuthService;
import com.foodgo.backend.module.auth.dto.mapper.AuthProfileMapper;
import com.foodgo.backend.security.jwt.JwtService;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.dto.mapper.UserAccountMapper;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import com.foodgo.backend.common.util.RandomUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  private final UserAccountMapper userAccountMapper;
  private final AuthProfileMapper authProfileMapper;
  private final HttpServletRequest httpRequest;

  @Value("${jwt.refresh-exp-days:7}") // Config ngày hết hạn refresh token
  private long refreshExpDays;

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
    var profile = authProfileMapper.toEntity(request);

    userAccount.setUsername(RandomUtils.generateUniqueUsername());
    userAccount.setPasswordHash(passwordEncoder.encode(request.plainTextPassword()));

    // Map 2 chiều (One to One)
    userAccount.setProfile(profile);
    userAccount.setRole(defaultRole);
    profile.setUserAccount(userAccount);

    var savedUser = userAccountRepository.save(userAccount);

    // 🔑 FIX: Tạo Session (Refresh Token) ngay sau khi đăng ký
    RefreshToken refreshToken = createRefreshToken(savedUser);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.REGISTRATION_SUCCESS, savedUser.getId()));

    // Truyền cả user và refreshToken vào
    return generateAuthResponse(savedUser, refreshToken);
  }

  @Override
  @Transactional
  public AuthResponse login(LoginRequest request) {
    // 0. TÌM USER
    var user =
        userAccountRepository
            .findByEmail(request.email())
            .orElseThrow(
                () -> new DataConflictException("Email chưa có tài khoản hoặc không hợp lệ"));

    // 🔒 KIỂM TRA TRẠNG THÁI TÀI KHOẢN
    if (Boolean.TRUE.equals(user.getIsDeleted())) {
      throw new UnauthorizedException("Tài khoản đã bị xóa. Vui lòng liên hệ quản trị viên.");
    }

    if (!user.isActive()) {
      throw new UnauthorizedException("Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.");
    }

    // 1. XÁC THỰC
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(), request.plainTextPassword()));

    // 2. LẤY USER
    var loginUser = (UserAccount) authentication.getPrincipal();

    // 3. TẠO SESSION (Refresh Token)
    RefreshToken refreshToken = createRefreshToken(loginUser);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.LOGIN_SUCCESSFUL, loginUser.getId()));

    // 4. TRẢ VỀ RESPONSE KÈM ACCESS TOKEN CHỨA RTID
    return generateAuthResponse(loginUser, refreshToken);
  }

  @Transactional
  @Override
  public AuthResponse refreshToken(RefreshTokenRequest request) {
    // 1. Tìm Refresh Token trong DB từ chuỗi token gửi lên
    RefreshToken storedToken =
        refreshTokenRepository
            .findByToken(request.refreshToken())
            .orElseThrow(() -> new ResourceNotFoundException("Refresh token không tồn tại"));

    // 2. Kiểm tra tính hợp lệ
    if (storedToken.isRevoked()) {
      // Cảnh báo bảo mật: Token đã bị hủy mà vẫn mang đi refresh -> Có thể bị đánh cắp
      throw new UnauthorizedException("Refresh token đã bị vô hiệu hóa. Vui lòng đăng nhập lại.");
    }

    if (storedToken.getExpiresAt().isBefore(Instant.now())) {
      throw new UnauthorizedException("Refresh token đã hết hạn. Vui lòng đăng nhập lại.");
    }

    // 3. Token Rotation (Xoay vòng): Hủy cái cũ, cấp cái mới
    storedToken.setRevoked(true);
    refreshTokenRepository.save(storedToken);

    UserAccount user = storedToken.getUser();
    RefreshToken newRefreshToken = createRefreshToken(user);

    SuccessMessageContext.setMessage("Làm mới token thành công");

    return generateAuthResponse(user, newRefreshToken);
  }

  @Override
  @Transactional
  public void logout() {
    // 1. Lấy Access Token từ Header
    String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return; // Không có token thì coi như đã logout
    }
    String accessToken = authHeader.substring(7);

    // 2. Lấy RTID từ Access Token
    Long rtid;
    try {
      rtid = jwtService.extractRefreshTokenId(accessToken);
    } catch (Exception e) {
      // Token lỗi không trích xuất được -> bỏ qua
      return;
    }

    // 3. Tìm và Hủy Session (Revoke Refresh Token)
    var storedToken = refreshTokenRepository.findById(rtid).orElse(null);
    if (storedToken != null) {
      storedToken.setRevoked(true);
      refreshTokenRepository.save(storedToken);
    }

    SuccessMessageContext.setMessage("Đăng xuất thành công");
  }

  // --- Helper Methods ---

  private RefreshToken createRefreshToken(UserAccount user) {
    RefreshToken refreshToken =
        RefreshToken.builder()
            .user(user)
            .token(UUID.randomUUID().toString()) // Token chuỗi ngẫu nhiên
            .expiresAt(Instant.now().plusSeconds(refreshExpDays * 24 * 60 * 60)) // 7 ngày
            .isRevoked(false)
            .build();
    return refreshTokenRepository.save(refreshToken);
  }

  private AuthResponse generateAuthResponse(UserAccount user, RefreshToken refreshToken) {
    // Truyền rtid vào Access Token
    String accessToken = jwtService.generateToken(user, refreshToken.getId());

    List<String> roles =
        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    // Trả về AccessToken + RefreshToken String (để client lưu)
    return new AuthResponse(accessToken, refreshToken.getToken(), roles);
  }
}
