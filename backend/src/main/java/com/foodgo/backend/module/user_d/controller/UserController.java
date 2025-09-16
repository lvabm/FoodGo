package com.foodgo.backend.module.user_d.controller;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.module.user_d.dto.AvatarUploadResponse;
import com.foodgo.backend.module.user_d.dto.UpdateProfileRequest;
import com.foodgo.backend.module.user_d.dto.UserResponse;
import com.foodgo.backend.module.user_d.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Lấy thông tin cá nhân")
  @GetMapping("/me")
  public ResponseEntity<BaseResponse<UserResponse>> getCurrentUser() {
    UserResponse data = userService.getCurrentUser();
    BaseResponse<UserResponse> body =
        BaseResponse.<UserResponse>builder()
            .success(true)
            .message("Lấy thông tin cá nhân")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Xem thông tin người dùng theo ID")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Long id) {
    UserResponse data = userService.getUserById(id);
    BaseResponse<UserResponse> body =
        BaseResponse.<UserResponse>builder()
            .success(true)
            .message("Lấy thông tin người dùng")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Cập nhật hồ sơ cá nhân")
  @PutMapping("/{id}")
  public ResponseEntity<BaseResponse<UserResponse>> updateProfile(
      @PathVariable Long id, @Valid @RequestBody UpdateProfileRequest request) {
    UserResponse data = userService.updateProfile(id, request);
    BaseResponse<UserResponse> body =
        BaseResponse.<UserResponse>builder()
            .success(true)
            .message("Cập nhật hồ sơ thành công")
            .data(data)
            .build();
    return ResponseEntity.ok(body);
  }

  @Operation(summary = "Tải ảnh đại diện")
  @PostMapping("/{id}/avatar")
  public ResponseEntity<BaseResponse<AvatarUploadResponse>> uploadAvatar(
      @PathVariable Long id, @RequestPart("file") MultipartFile file) {
    AvatarUploadResponse data = userService.uploadAvatar(id, file);
    BaseResponse<AvatarUploadResponse> body =
        BaseResponse.<AvatarUploadResponse>builder()
            .success(true)
            .message("Upload avatar thành công")
            .data(data)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }
}
