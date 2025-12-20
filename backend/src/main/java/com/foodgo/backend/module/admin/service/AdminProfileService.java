package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.user.dto.request.filter.ProfileFilterRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;

public interface AdminProfileService
    extends ReadableService<Long, ProfileFilterRequest, ProfileResponse> {}
