package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.mapper.OutletFeatureMappingMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OutletFeatureMappingCreateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import com.foodgo.backend.module.outlet.repository.OutletFeatureMappingRepository;
import com.foodgo.backend.module.outlet.repository.OutletFeatureRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletFeatureMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OutletFeatureMappingServiceImpl implements OutletFeatureMappingService {

  private final OutletFeatureMappingRepository mappingRepository;
  private final OutletRepository outletRepository;
  private final OutletFeatureRepository featureRepository;
  private final OutletFeatureMappingMapper mappingMapper;

  private final String entityName = "Outlet Feature Mapping";

  // --- Helper Logic (Security) ---

  /** Kiểm tra quyền Owner/Admin của Outlet và trả về Outlet Entity */
  private Outlet findOutletAndEnsurePermission(UUID outletId) {
    Outlet outlet =
        outletRepository
            .findById(outletId)
            .orElseThrow(() -> new ResourceNotFoundException("Outlet" + "id: " + outletId));

    UUID currentUserId = SecurityContext.getCurrentUserId();

    // 1. Admin Bypass
    if (SecurityContext.isAdmin()) {
      return outlet;
    }

    // 2. Ownership Check
    if (!outlet.getOwner().getId().equals(currentUserId)) {
      throw new AccessDeniedException("Bạn không có quyền thao tác Outlet" + " id: " + outletId);
    }
    return outlet;
  }

  private OutletFeature findFeatureOrThrow(Integer featureId) {
    return featureRepository
        .findById(featureId)
        .orElseThrow(() -> new ResourceNotFoundException("OutletFeature" + " id: " + featureId));
  }

  // ==================== I. ADD FEATURE (CREATE) ====================

  @Override
  public OutletFeatureMappingResponse addFeatureToOutlet(
      UUID outletId, OutletFeatureMappingCreateRequest request) {
    // 1. Kiểm tra quyền sở hữu Outlet
    Outlet outlet = findOutletAndEnsurePermission(outletId);

    // 2. Kiểm tra sự tồn tại của Feature
    OutletFeature feature = findFeatureOrThrow(request.featureId());

    // 3. Kiểm tra trùng lặp
    if (mappingRepository.existsByOutletIdAndFeatureId(outletId, request.featureId())) {
      throw new IllegalArgumentException(String.format("Outlet %s đã có đặc điểm này.", outletId));
    }

    // 4. Mapping và Gán
    OutletFeatureMapping mapping = mappingMapper.toEntity(request);
    mapping.setOutlet(outlet);
    mapping.setFeature(feature);

    OutletFeatureMapping savedMapping = mappingRepository.save(mapping);

    // HARD RULE: Success Message
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, entityName, savedMapping.getId()));

    return mappingMapper.toResponse(savedMapping);
  }

  // ==================== II. REMOVE FEATURE (HARD DELETE) ====================

  @Override
  public void removeFeatureFromOutlet(UUID outletId, Integer featureId) {
    // 1. Kiểm tra quyền sở hữu Outlet
    findOutletAndEnsurePermission(outletId);

    // 2. Tìm Mapping để xóa
    OutletFeatureMapping mapping =
        mappingRepository
            .findByOutletIdAndFeatureId(outletId, featureId)
            .orElseThrow(
                () -> new ResourceNotFoundException(entityName + " featureId: " + featureId));

    // 3. Xóa
    mappingRepository.delete(mapping);

    // HARD RULE: Success Message (Dùng Hard Delete Message)
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.HARD_DELETE_SUCCESS, entityName, featureId));
  }
}
