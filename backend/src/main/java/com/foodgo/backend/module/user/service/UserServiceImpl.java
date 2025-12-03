package com.foodgo.backend.module.user.service;

import com.foodgo.backend.module.user.dto.*;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserAccountRepository userAccountRepository;

  @Override
  public UserResponse getCurrentUser() {
    // TODO: lấy user từ SecurityContext, map sang UserResponse
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public UserResponse getOtherUserById(Long id) {
    // TODO: fetch user; nếu admin trả full view, nếu public trả public view
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public UserResponse updateProfile(Long id, UpdateProfileRequest request) {
    // TODO: kiểm tra quyền (owner hoặc admin), cập nhật và trả UserResponse
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  @Transactional
  public AvatarUploadResponse uploadAvatar(Long id, MultipartFile file) {
    // TODO: validate file size/type, lưu file và trả AvatarUploadResponse
    throw new UnsupportedOperationException("Not implemented");
  }
}
