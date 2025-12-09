package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.service.BaseMapper;
import com.foodgo.backend.common.base.service.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.request.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletRequest;
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
        Outlet, OutletRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse, UUID>
    implements OutletService {

  private final String outletEntityName = EntityName.OUTLET.getFriendlyName();
  private final OutletRepository outletRepository;
  private final OutletMapper outletMapper;
  private final UserAccountRepository userAccountRepository; // Cần để tải Owner
  private final DistrictRepository districtRepository;
  private final OutletTypeRepository outletTypeRepository;

  // --- Triển khai Abstract Methods ---

  @Override
  protected JpaRepository<Outlet, UUID> getRepository() {
    return outletRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Outlet> getSpecRepository() {
    // OutletRepository cần extends JpaSpecificationExecutor
    return outletRepository;
  }

  @Override
  protected BaseMapper<Outlet, OutletRequest, OutletUpdateRequest, OutletResponse> getMapper() {
    return outletMapper;
  }

  @Override
  protected String getEntityName() {
    return outletEntityName;
  }

  // --- Triển khai Hook Methods/Custom CRUD Logic ---

  // 1. Ghi đè buildSpecification để sử dụng OutletSearchSpecification
  @Override
  protected Specification<Outlet> buildSpecification(OutletFilterRequest filterRequest) {
    return new OutletSearchSpecification(filterRequest);
  }

  // 2. Custom Create (Gán Owner ID)
  @Override
  @Transactional
  public OutletResponse createOutlet(OutletRequest request, UUID ownerId) {
    // Validation: Kiểm tra ID ngoại lai tồn tại
    if (!districtRepository.existsById(request.districtId())) {
      throw new ResourceNotFoundException("District" + " id: " + request.districtId());
    }
    if (!outletTypeRepository.existsById(request.typeId())) {
      throw new ResourceNotFoundException("OutletType" + " id: " + request.typeId());
    }

    // Tải Owner Entity
    UserAccount owner =
        userAccountRepository
            .findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

    Outlet entity = outletMapper.toEntity(request);

    // Gán các Entity quan hệ
    entity.setOwner(owner);
    entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
    entity.setType(outletTypeRepository.getReferenceById(request.typeId()));

    Outlet savedEntity = outletRepository.save(entity);
    return outletMapper.toResponse(savedEntity);
  }

  // 3. Custom Update (Kiểm tra quyền sở hữu)
  @Override
  @Transactional
  public OutletResponse updateOutlet(UUID id, OutletUpdateRequest request, UUID ownerId) {
    // 🔑 Hook: Kiểm tra quyền sở hữu trước khi cập nhật
    validateOwnership(id, ownerId);

    Outlet entity = findByIdOrThrow(id);

    // 1. Cập nhật các ID ngoại lai có điều kiện (chỉ khi chúng được cung cấp trong request)

    // Cập nhật District
    request
        .optionalDistrictId()
        .ifPresent(
            districtId -> {
              if (!districtRepository.existsById(districtId)) {
                throw new ResourceNotFoundException("District" + " id: " + districtId);
              }
              entity.setDistrict(districtRepository.getReferenceById(districtId));
            });

    // Cập nhật Outlet Type
    request
        .optionalTypeId()
        .ifPresent(
            typeId -> {
              if (!outletTypeRepository.existsById(typeId)) {
                throw new ResourceNotFoundException("OutletType" + " id: " + typeId);
              }
              entity.setType(outletTypeRepository.getReferenceById(typeId));
            });

    // 2. Sử dụng MapStruct updateEntity để cập nhật các trường cơ bản (sử dụng
    // nullValuePropertyMappingStrategy.IGNORE)
    outletMapper.updateEntity(request, entity);

    Outlet updatedEntity = getRepository().save(entity);
    return outletMapper.toResponse(updatedEntity);
  }

  // 4. Kiểm tra quyền sở hữu cho các thao tác DELETE
  @Override
  protected void validateBeforeUpdate(UUID id, OutletUpdateRequest updateRequest) {
    // Logic kiểm tra quyền sở hữu cho PUT được chuyển sang updateOutlet(..., ownerId)
  }

  @Override
  @Transactional
  public OutletResponse softDelete(UUID id) {
    // Lấy Owner ID từ Security Context để kiểm tra quyền
    // NOTE: Cần truyền Owner ID từ Controller xuống Service (Tùy thuộc vào thiết kế)
    // Hiện tại: Giả định Service Layer có thể lấy Owner ID nếu cần, hoặc Controller sẽ handle
    // Tốt hơn: Tạo softDelete(Id id, UUID ownerId) trong BaseService nếu cần kiểm tra quyền ở Base
    return super.softDelete(id); // Dùng Base logic tạm thời
  }

  // --- Helper Method cho Security ---
  private void validateOwnership(UUID outletId, UUID currentOwnerId) {
    Outlet outlet = findByIdOrThrow(outletId);
    if (!outlet.getOwner().getId().equals(currentOwnerId)) {
      // Sử dụng AccessDeniedException hoặc ForbiddenException
      throw new ResourceNotFoundException("Outlet" + " id: " + outletId);
      // Dùng ResourceNotFoundException để ẩn đi thông tin về quyền sở hữu (Security by obscurity)
    }
  }
}
