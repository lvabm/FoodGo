package com.foodgo.backend.module.user.service;

import com.foodgo.backend.module.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  // Lấy profile người đang đăng nhập
  UserResponse getCurrentUser();

  // Lấy profile theo id (public view tuỳ role)
  UserResponse getOtherUserById(Long id);

  // Cập nhật hồ sơ (validate owner or admin)
  UserResponse updateProfile(Long id, UpdateProfileRequest request);

  // Upload avatar, validate size/type
  AvatarUploadResponse uploadAvatar(Long id, MultipartFile file);
}
