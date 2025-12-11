package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.criteria.OperatingHoursSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OperatingHoursMapper;
import com.foodgo.backend.module.outlet.dto.request.create.OperatingHoursCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OperatingHoursFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OperatingHoursUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OperatingHoursResponse;
import com.foodgo.backend.module.outlet.entity.OperatingHours;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OperatingHoursRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OperatingHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatingHoursServiceImpl
    extends BaseServiceImpl<
        OperatingHours,
        OperatingHoursCreateRequest,
        OperatingHoursUpdateRequest,
        OperatingHoursFilterRequest,
        OperatingHoursResponse,
        Integer>
    implements OperatingHoursService {

  private final OperatingHoursRepository repository;
  private final OperatingHoursMapper mapper;
  private final OutletRepository outletRepository;

  private final String operatingHoursEntityName = EntityName.OPERATING_HOURS.getFriendlyName();

  // --- Abstract Methods ---
  @Override
  protected JpaRepository<OperatingHours, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<OperatingHours> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<
          OperatingHours,
          OperatingHoursCreateRequest,
          OperatingHoursUpdateRequest,
          OperatingHoursResponse>
      getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return operatingHoursEntityName;
  }

  // --- Hooks & Specification ---

  /** 🔑 HOOK: Kiểm tra quyền sở hữu/Admin của Outlet sở hữu giờ hoạt động */
  @Override
  protected void ensurePermission(OperatingHours entity) {
    UUID currentUserId = SecurityContext.getCurrentUserId();

    if (SecurityContext.isAdmin()) {
      return;
    }

    // Giờ hoạt động thuộc Outlet nào, thì phải là Owner của Outlet đó mới có quyền sửa
    if (!entity.getOutlet().getOwner().getId().equals(currentUserId)) {
      throw new ResourceNotFoundException(
          getEntityName() + " không tìm thấy với ID: " + entity.getId());
    }
  }

  // Validation tùy chỉnh cho thời gian
  @Override
  protected void validateBeforeCreate(OperatingHoursCreateRequest request) {
    if (!request.isClosed() && request.openTime().isAfter(request.closeTime())) {
      throw new IllegalArgumentException("Giờ mở cửa phải trước giờ đóng cửa.");
    }
  }

  @Override
  protected Specification<OperatingHours> buildSpecification(
      OperatingHoursFilterRequest filterRequest) {
    return new OperatingHoursSearchSpecification(filterRequest);
  }

  // --- Ghi đè CREATE để gán FK ---
  @Override
  @Transactional
  public OperatingHoursResponse create(OperatingHoursCreateRequest request) {
    // 1. Kiểm tra Outlet tồn tại
    Outlet outlet =
        outletRepository
            .findById(request.outletId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Outlet" + " id: " + request.outletId()));

    // 2. Kiểm tra quyền sở hữu Outlet (Hook chỉ kiểm tra quyền trên Entity đã tồn tại, nên ta kiểm
    // tra quyền ở đây)
    if (!SecurityContext.isAdmin()
        && !outlet.getOwner().getId().equals(SecurityContext.getCurrentUserId())) {
      throw new AccessDeniedException("Bạn không có quyền thêm giờ hoạt động cho Outlet này.");
    }

    validateBeforeCreate(request); // Kiểm tra thời gian

    OperatingHours entity = mapper.toEntity(request);
    entity.setOutlet(outlet);

    OperatingHours savedEntity = repository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId()));

    return mapper.toResponse(savedEntity);
  }

  // --- Ghi đè UPDATE để validation (BaseServiceImpl tự gọi ensurePermission) ---
  @Override
  protected void validateBeforeUpdate(Integer id, OperatingHoursUpdateRequest request) {
    // Validation thời gian cho Partial Update

    // Cần tải Entity để so sánh
    OperatingHours entity = findByIdOrThrow(id);

    LocalTime newOpenTime = request.optionalOpenTime().orElse(entity.getOpenTime());
    LocalTime newCloseTime = request.optionalCloseTime().orElse(entity.getCloseTime());
    Boolean newIsClosed = request.optionalIsClosed().orElse(entity.getIsClosed());

    if (!newIsClosed && newOpenTime.isAfter(newCloseTime)) {
      throw new IllegalArgumentException("Giờ mở cửa phải trước giờ đóng cửa.");
    }
  }
}
