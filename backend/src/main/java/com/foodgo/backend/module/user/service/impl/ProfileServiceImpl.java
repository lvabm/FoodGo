package com.foodgo.backend.module.user.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.facade.CurrentUserDataFacade;
import com.foodgo.backend.module.location.repository.CountryRepository;
import com.foodgo.backend.module.user.dto.mapper.ProfileMapper;
import com.foodgo.backend.module.user.dto.request.update.ProfileUpdateRequest;
import com.foodgo.backend.module.user.dto.response.ProfileResponse;
import com.foodgo.backend.module.user.entity.Profile;
import com.foodgo.backend.module.user.repository.ProfileRepository;
import com.foodgo.backend.module.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;
  private final ProfileMapper profileMapper;
  private final CountryRepository countryRepository;
  private final CurrentUserDataFacade userDataFacade;

  private final String profileEntityName = EntityName.PROFILE.getFriendlyName();

  protected void ensurePermission(Profile entity) {
    // Chỉ Admin mới được bỏ qua (Admin Bypass)
    if (SecurityContext.isAdmin()) return;

    // Ownership Check: User chỉ được cập nhật Profile của chính mình
    if (!entity.getUserAccount().getId().equals(SecurityContext.getCurrentUserId())) {
      throw new ResourceNotFoundException(
          profileEntityName + " không tìm thấy với ID: " + entity.getId());
    }
  }

  // --- Custom UPDATE/READ Logic ---

  @Override
  @Transactional
  public ProfileResponse update(ProfileUpdateRequest request) {

    // 1. Validation FK tồn tại
    request
        .optionalCountryId()
        .ifPresent(
            countryId -> {
              if (!countryRepository.existsById(countryId)) {
                throw new ResourceNotFoundException("Country" + " id: " + countryId);
              }
            });

    // 2. Gán Entity FK (Nếu có)
    Profile myProfile = (Profile) userDataFacade.getMyProfile();
    ensurePermission(myProfile); // Hook kiểm tra quyền

    request
        .optionalCountryId()
        .ifPresent(
            countryId -> myProfile.setCountry(countryRepository.getReferenceById(countryId)));

    profileMapper.updateEntity(request, myProfile);
    Profile updatedEntity = profileRepository.save(myProfile);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, profileEntityName, myProfile.getId()));

    return profileMapper.toResponse(updatedEntity);
  }

  @Override
  @Transactional
  public ProfileResponse getMyProfile() {

    Profile profile = (Profile) userDataFacade.getMyProfile();

    if (profile == null) {
      throw new ResourceNotFoundException(
          profileEntityName + " userId: " + SecurityContext.getCurrentUserId());
    }

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.FETCH_DETAIL_SUCCESS, profileEntityName, profile.getId()));

    return profileMapper.toResponse(profile);
  }
}
