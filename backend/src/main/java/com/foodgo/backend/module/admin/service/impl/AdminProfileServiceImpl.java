package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.admin.service.AdminProfileService;
import com.foodgo.backend.module.location.repository.CountryRepository;
import com.foodgo.backend.module.user.dto.criteria.ProfileSearchSpecification;
import com.foodgo.backend.module.user.dto.mapper.ProfileMapper;
import com.foodgo.backend.module.user.dto.request.filter.ProfileFilterRequest;
import com.foodgo.backend.module.user.dto.request.update.ProfileUpdateRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;
import com.foodgo.backend.module.user.entity.Profile;
import com.foodgo.backend.module.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProfileServiceImpl
    extends BaseServiceImpl<
        Profile, Long, Object, ProfileUpdateRequest, ProfileFilterRequest, ProfileResponse>
    implements AdminProfileService {

  private final ProfileRepository repository;
  private final ProfileMapper mapper;
  private final CountryRepository countryRepository;

  private final String profileEntityName = EntityName.PROFILE.getFriendlyName() + " (Admin)";

  // --- Abstract Methods (BaseServiceImpl) ---
  @Override
  protected JpaRepository<Profile, Long> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<Profile> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<Profile, Object, ProfileUpdateRequest, ProfileResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return profileEntityName;
  }

  // --- Hooks & Logic ---

  @Override
  protected void ensurePermission(Profile entity) {
    // Chỉ cần đảm bảo người gọi là Admin
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException("Chỉ Admin mới có quyền thao tác này.");
    }
  }

  @Override
  protected Specification<Profile> buildSpecification(ProfileFilterRequest filterRequest) {
    return new ProfileSearchSpecification(filterRequest);
  }
}
