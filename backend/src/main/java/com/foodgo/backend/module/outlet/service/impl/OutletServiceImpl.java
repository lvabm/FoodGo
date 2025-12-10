package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.location.repository.DistrictRepository;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
import com.foodgo.backend.module.outlet.service.OutletService;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
// Kế thừa BaseServiceImpl: Entity=Outlet, Request/Update=OutletUpdateRequest,
// Filter=OutletFilterRequest,
// Response=OutletResponse, Id=UUID
public class OutletServiceImpl
    extends BaseServiceImpl<
        Outlet, OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse, UUID>
    implements OutletService {

  private final String outletEntityName = EntityName.OUTLET.getFriendlyName();
  private final OutletRepository outletRepository;
  private final OutletMapper outletMapper;
  private final UserAccountRepository userAccountRepository; // Cần để tải Owner
  private final DistrictRepository districtRepository;
  private final OutletTypeRepository outletTypeRepository;

  // --- Abstract Methods ---
  @Override
  protected JpaRepository<Outlet, UUID> getRepository() {
    return outletRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Outlet> getSpecRepository() {
    return outletRepository;
  }

  @Override
  protected BaseMapper<Outlet, OutletCreateRequest, OutletUpdateRequest, OutletResponse>
      getMapper() {
    return outletMapper;
  }

  @Override
  protected String getEntityName() {
    return outletEntityName;
  }

  // ==================== I. HOOK METHODS (SECURITY/PERMISSION) ====================

  /** 🔑 HARD RULE: Kiểm tra quyền sở hữu hoặc quyền Admin (Admin Bypass) */
  @Override
  protected void ensurePermission(Outlet entity) {
    UUID currentUserId = SecurityContext.getCurrentUserId();

    // 1. Admin Bypass
    if (SecurityContext.isAdmin()) {
      return;
    }

    // 2. Kiểm tra Ownership (Rule: Owner có thể modify own data)
    if (!entity.getOwner().getId().equals(currentUserId)) {
      // Ném lỗi 404 để ẩn thông tin về quyền sở hữu (Security by obscurity)
      throw new ResourceNotFoundException(
          getEntityName() + " không tìm thấy với ID: " + entity.getId());
    }
  }

  // ==================== II. GHI ĐÈ CRUD CỐT LÕI (FK Assignment) ====================

  /** Ghi đè CREATE để gán Owner Entity và FK Entities (District, OutletType) */
  @Override
  @Transactional
  public OutletResponse create(OutletCreateRequest request) {
    // 1. Lấy Owner từ SecurityContext (Service Rule)
    UUID ownerId = SecurityContext.getCurrentUserId();
    UserAccount owner =
        userAccountRepository
            .findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

    // 2. Validate FK tồn tại
    if (!districtRepository.existsById(request.districtId())) {
      throw new ResourceNotFoundException("District" + " id: " + request.districtId());
    }
    if (!outletTypeRepository.existsById(request.typeId())) {
      throw new ResourceNotFoundException("OutletType" + " id: " + request.typeId());
    }

    // 3. Mapping DTO và GÁN Entity quan hệ
    Outlet entity = outletMapper.toEntity(request);
    entity.setOwner(owner);
    entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
    entity.setType(outletTypeRepository.getReferenceById(request.typeId()));

    // 4. Lưu và hoàn tất (dùng Base Logic để set message)
    Outlet savedEntity = outletRepository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));

    return outletMapper.toResponse(savedEntity);
  }

  @Override
  @Transactional
  public OutletResponse update(UUID id, OutletUpdateRequest request) {

    Outlet entity = findByIdOrThrow(id);
    ensurePermission(entity);

    // Gán District
    request
        .optionalDistrictId()
        .ifPresent(
            districtId -> {
              if (!districtRepository.existsById(districtId)) {
                throw new ResourceNotFoundException("District" + " id: " + districtId);
              }
              entity.setDistrict(districtRepository.getReferenceById(districtId));
            });

    // Gán Outlet Type
    request
        .optionalTypeId()
        .ifPresent(
            typeId -> {
              if (!outletTypeRepository.existsById(typeId)) {
                throw new ResourceNotFoundException("OutletType" + " id: " + typeId);
              }
              entity.setType(outletTypeRepository.getReferenceById(typeId));
            });

    outletMapper.updateEntity(request, entity);

    Outlet updatedEntity = getRepository().save(entity);
    afterUpdate(updatedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return outletMapper.toResponse(updatedEntity);
  }

  // ==================== III. SPECIFICATION ====================

  @Override
  protected Specification<Outlet> buildSpecification(OutletFilterRequest filterRequest) {
    // Giả định đã có OutletSearchSpecification
    return new OutletSearchSpecification(filterRequest);
  }
}
