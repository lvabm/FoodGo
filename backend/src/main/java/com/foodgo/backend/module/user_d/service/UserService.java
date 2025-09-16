package com.foodgo.backend.module.user_d.service;

import com.foodgo.backend.module.user_d.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  // Lấy profile người đang đăng nhập
  UserResponse getCurrentUser();

  // Lấy profile theo id (public/admin view tuỳ role)
  UserResponse getUserById(Long id);

  // Cập nhật hồ sơ (validate owner or admin)
  UserResponse updateProfile(Long id, UpdateProfileRequest request);

  // Upload avatar, validate size/type
  AvatarUploadResponse uploadAvatar(Long id, MultipartFile file);
}
