package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.location.repository.DistrictRepository;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
import com.foodgo.backend.module.outlet.service.OutletService;
import com.foodgo.backend.module.user.entity.Role;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutletServiceImpl
    extends BaseServiceImpl<
        Outlet, UUID, OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse>
    implements OutletService {

  private final OutletRepository outletRepository;
  private final OutletMapper outletMapper;
  private final UserAccountRepository userAccountRepository;
  private final DistrictRepository districtRepository;
  private final OutletTypeRepository outletTypeRepository;
  private final RoleRepository roleRepository;

  private final String outletEntityName = EntityName.OUTLET.getFriendlyName();

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

  // ==================== I. HOOK METHODS (SECURITY) ====================

  @Override
  protected void ensurePermission(Outlet entity) {
    UUID currentUserId = SecurityContext.getCurrentUserId();

    // Admin bypass mọi quyền sở hữu
    if (SecurityContext.isAdmin()) {
      return;
    }

    if (!entity.getOwner().getId().equals(currentUserId)) {
      throw new AccessDeniedException(
          "Bạn không có quyền thao tác Outlet" + " id: " + entity.getId());
    }
  }

  // ==================== II. GHI ĐÈ CRUD CỐT LÕI ====================

  @Override
  @Transactional
  public OutletResponse create(OutletCreateRequest request) {
    // 1. Lấy Owner từ SecurityContext
    UUID ownerId = SecurityContext.getCurrentUserId();
    UserAccount owner =
        userAccountRepository
            .findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

    // [FIX BUG] 1.5: Tự động thăng cấp ROLE_OWNER (trừ khi là Admin)
    promoteToOwnerIfNecessary(owner);

    // 2. Validate FK
    if (!districtRepository.existsById(request.districtId())) {
      throw new ResourceNotFoundException("District" + " id: " + request.districtId());
    }
    if (!outletTypeRepository.existsById(request.typeId())) {
      throw new ResourceNotFoundException("OutletType" + " id: " + request.typeId());
    }

    // 3. Mapping & FK Assignment
    Outlet entity = outletMapper.toEntity(request);
    entity.setOwner(owner);
    entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
    entity.setType(outletTypeRepository.getReferenceById(request.typeId()));

    // 4. Save & Success Message
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
    ensurePermission(entity); // Check quyền trước khi update

    request
        .optionalDistrictId()
        .ifPresent(
            districtId -> {
              if (!districtRepository.existsById(districtId)) {
                throw new ResourceNotFoundException("District" + " id: " + districtId);
              }
              entity.setDistrict(districtRepository.getReferenceById(districtId));
            });

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
    return new OutletSearchSpecification(filterRequest);
  }

  // ==================== IV. PRIVATE HELPERS (BUG FIXED) ====================

  /**
   * Helper method: Kiểm tra và thêm Role OWNER cho user thường. KHÔNG áp dụng nếu user là ADMIN.
   */
  private void promoteToOwnerIfNecessary(UserAccount user) {
    // 1. Guard Clause: Nếu là Admin -> Return ngay, không làm gì cả.
    boolean isAdmin =
        user.getRole().getName().equalsIgnoreCase(RoleType.ROLE_SYSTEM_ADMIN.getName());

    if (isAdmin) {
      return;
    }

    // 2. Logic cho User thường: Nếu chưa là Owner thì cấp quyền.
    boolean hasOwnerRole = user.getRole().getName().equalsIgnoreCase(RoleType.ROLE_OWNER.getName());

    if (!hasOwnerRole) {
      Role ownerRole =
          roleRepository
              .findByName(RoleType.ROLE_OWNER.getName())
              .orElseThrow(
                  () -> new ResourceNotFoundException("Role OWNER configuration not found in DB"));

      user.setRole(ownerRole);
      userAccountRepository.save(user);
    }
  }

  @Override
  public List<OutletResponse> getOwnerOutlets() {
    UUID ownerId = SecurityContext.getCurrentUserId();

    List<Outlet> outlets = outletRepository.findAllByOwnerId(ownerId);

    if (outlets.isEmpty()) {
      throw new ResourceNotFoundException("Bạn chưa tạo quán nào. Vui lòng tạo quán của bạn.");
    }

    return outlets.stream().map(outletMapper::toResponse).toList();
  }
}
