package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.mapper.OutletFeatureMappingMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletFeatureMappingRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletFeatureMappingResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import com.foodgo.backend.module.outlet.repository.OutletFeatureMappingRepository;
import com.foodgo.backend.module.outlet.repository.OutletFeatureRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletFeatureMappingService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OutletFeatureMappingServiceImpl implements OutletFeatureMappingService {

  private final OutletFeatureMappingRepository mappingRepository;
  private final OutletRepository outletRepository;
  private final OutletFeatureRepository featureRepository;

  private final OutletFeatureMappingMapper mapper;

  // Hàm hỗ trợ kiểm tra quyền sở hữu Outlet (có thể tái sử dụng từ OutletServiceImpl)
  private Outlet findOutletAndValidateOwnership(UUID outletId, UUID ownerId) {
    Outlet outlet =
        outletRepository
            .findById(outletId)
            .orElseThrow(() -> new ResourceNotFoundException("Outlet" + " id " + outletId));

    if (!outlet.getOwner().getId().equals(ownerId)) {
      // Ném Forbidden/AccessDenied nếu không phải Owner
      throw new AccessDeniedException(
          "Chủ sở hữu không có quyền thay đổi đặc điểm của Outlet này.");
    }
    return outlet;
  }

  // Hàm hỗ trợ tìm Feature
  private OutletFeature findFeatureOrThrow(Integer featureId) {
    return featureRepository
        .findById(featureId)
        .orElseThrow(() -> new ResourceNotFoundException("OutletFeature" + " id: " + featureId));
  }

  @Override
  public OutletFeatureMappingResponse addFeatureToOutlet(
      UUID outletId, OutletFeatureMappingRequest request, UUID ownerId) {
    // 1. Kiểm tra quyền sở hữu Outlet
    Outlet outlet = findOutletAndValidateOwnership(outletId, ownerId);

    // 2. Kiểm tra sự tồn tại của Feature
    OutletFeature feature = findFeatureOrThrow(request.featureId());

    // 3. Kiểm tra trùng lặp (nếu đã tồn tại mapping)
    if (mappingRepository.existsByOutletIdAndFeatureId(outletId, request.featureId())) {
      throw new IllegalArgumentException(
          String.format("Outlet %s đã có đặc điểm %d.", outletId, request.featureId()));
    }

    // 4. Mapping
    OutletFeatureMapping mapping = mapper.toEntity(request);
    mapping.setOutlet(outlet);
    mapping.setFeature(feature);

    OutletFeatureMapping saved = mappingRepository.save(mapping);

    return mapper.toResponse(saved);
  }

  @Override
  public void removeFeatureFromOutlet(UUID outletId, Integer featureId, UUID ownerId) {
    // 1. Kiểm tra quyền sở hữu Outlet
    findOutletAndValidateOwnership(outletId, ownerId);

    // 2. Tìm Mapping để xóa
    OutletFeatureMapping mapping =
        mappingRepository
            .findByOutletIdAndFeatureId(outletId, featureId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Mapping" + " featureId " + featureId));

    // 3. Xóa cứng
    mappingRepository.delete(mapping);
  }
}
