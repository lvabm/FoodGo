package com.foodgo.backend.module.user.service;

import com.foodgo.backend.module.user.dto.request.update.ProfileUpdateRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;

public interface ProfileService {

  ProfileResponse update(ProfileUpdateRequest request);

  ProfileResponse getMyProfile();
}
