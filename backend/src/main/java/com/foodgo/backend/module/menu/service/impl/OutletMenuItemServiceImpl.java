package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.menu.entity.MenuItem;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.menu.repository.MenuItemRepository;
import com.foodgo.backend.module.menu.repository.OutletMenuItemRepository;
import com.foodgo.backend.module.menu.dto.criteria.OutletMenuItemSearchSpecification;
import com.foodgo.backend.module.menu.dto.mapper.OutletMenuItemMapper;
import com.foodgo.backend.module.menu.dto.request.OutletMenuItemCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletMenuItemFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletMenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.menu.service.OutletMenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutletMenuItemServiceImpl
    extends BaseServiceImpl<
        OutletMenuItem,
        OutletMenuItemCreateRequest,
        OutletMenuItemUpdateRequest,
        OutletMenuItemFilterRequest,
        OutletMenuItemResponse,
        Integer>
    implements OutletMenuItemService {

  private final OutletMenuItemRepository outletMenuItemRepository;
  private final OutletMenuItemMapper outletMenuItemMapper;
  private final OutletRepository outletRepository;
  private final MenuItemRepository menuItemRepository;

  private final String outletMenuItemEntityName = EntityName.OUTLET_MENU_ITEM.getFriendlyName();

  // --- Abstract Methods ---
  @Override
  protected JpaRepository<OutletMenuItem, Integer> getRepository() {
    return outletMenuItemRepository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletMenuItem> getSpecRepository() {
    return outletMenuItemRepository;
  }

  @Override
  protected BaseMapper<
          OutletMenuItem,
          OutletMenuItemCreateRequest,
          OutletMenuItemUpdateRequest,
          OutletMenuItemResponse>
      getMapper() {
    return outletMenuItemMapper;
  }

  @Override
  protected String getEntityName() {
    return outletMenuItemEntityName;
  }

  // --- Hooks & Specification ---

  // 🔑 HOOK: Triển khai Logic kiểm tra quyền sở hữu/Admin
  @Override
  protected void ensurePermission(OutletMenuItem entity) {
    UUID currentUserId = SecurityContext.getCurrentUserId();

    if (SecurityContext.isAdmin()) {
      return;
    }

    // Món ăn thuộc Outlet nào, thì phải là Owner của Outlet đó mới có quyền sửa
    if (!entity.getOutlet().getOwner().getId().equals(currentUserId)) {
      throw new ResourceNotFoundException(
          getEntityName() + " không tìm thấy với ID: " + entity.getId());
    }
  }

  @Override
  protected Specification<OutletMenuItem> buildSpecification(
      OutletMenuItemFilterRequest filterRequest) {
    return new OutletMenuItemSearchSpecification(filterRequest);
  }

  // --- Ghi đè CREATE để gán Owner và FK ---
  @Override
  @Transactional
  public OutletMenuItemResponse create(OutletMenuItemCreateRequest request) {
    // 1. Kiểm tra Owner của Outlet (để đảm bảo User đang tạo món ăn cho Outlet mình sở hữu)
    UUID ownerId = SecurityContext.getCurrentUserId();

    Outlet outlet =
        outletRepository
            .findById(request.outletId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Outlet" + " id: " + request.outletId()));

    if (!outlet.getOwner().getId().equals(ownerId)) {
      throw new AccessDeniedException("Bạn không có quyền thêm món ăn vào Outlet này.");
    }

    // 2. Validate FK tồn tại và kiểm tra trùng lặp
    MenuItem menuItem =
        menuItemRepository
            .findById(request.menuItemId())
            .orElseThrow(
                () -> new ResourceNotFoundException("MenuItem" + " id: " + request.menuItemId()));

    if (outletMenuItemRepository.existsByOutletIdAndMenuItemId(
        request.outletId(), request.menuItemId())) {
      throw new IllegalArgumentException("Món ăn này đã có trong Menu của Outlet.");
    }

    // 3. Mapping và gán Entity
    OutletMenuItem entity = outletMenuItemMapper.toEntity(request);
    entity.setOutlet(outlet);
    entity.setMenuItem(menuItem);
    entity.setIsAvailable(true); // Default

    // 4. Lưu và hoàn tất
    OutletMenuItem savedEntity = outletMenuItemRepository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));

    return outletMenuItemMapper.toResponse(savedEntity);
  }

  // --- Ghi đè UPDATE để gán lại FK nếu cần ---
  @Override
  @Transactional
  public OutletMenuItemResponse update(Integer id, OutletMenuItemUpdateRequest request) {
    // 1. BaseServiceImpl.update gọi findByIdOrThrow -> ensurePermission (kiểm tra quyền Owner)
    OutletMenuItemResponse response = super.update(id, request);

    // 2. Cập nhật Entity FK nếu menuItemId được cung cấp (Partial Update Logic)
    request
        .optionalMenuItemId()
        .ifPresent(
            menuItemId -> {
              if (!menuItemRepository.existsById(menuItemId)) {
                throw new ResourceNotFoundException("MenuItem" + " id " + menuItemId);
              }
              OutletMenuItem entity = findByIdOrThrow(id); // Lấy lại Entity để gán
              entity.setMenuItem(menuItemRepository.getReferenceById(menuItemId));

              // Lưu lại sau khi gán FK
              getRepository().save(entity);
            });

    // Trả về Response
    // Do BaseService đã set Message, ta chỉ cần trả về Response mới nhất (cần refetch/map)
    return getMapper().toResponse(findByIdOrThrow(id));
  }

  // Api tùy chỉnh
  @Override
  @Transactional
  public OutletMenuItemResponse toggleAvailability(Integer itemId) {
    // 1. Tìm Entity và kiểm tra quyền sở hữu (dùng Hook ensurePermission)
    OutletMenuItem entity = findByIdOrThrow(itemId);
    ensurePermission(entity);

    // 2. 🔑 LOGIC CHUYỂN ĐỔI: Đọc trạng thái hiện tại và chuyển đổi ngược lại
    boolean newState = !entity.getIsAvailable();

    entity.setIsAvailable(newState);
    getRepository().save(entity);

    // 3. Set Success Message (Dùng format Update)
    String action = newState ? "Bật (Available)" : "Tắt (Unavailable)";
    com.foodgo.backend.common.context.SuccessMessageContext.setMessage(
        String.format(
            com.foodgo.backend.common.context.SuccessMessageContext.UPDATE_SUCCESS,
            getEntityName(),
            itemId + " (" + action + ")"));

    return getMapper().toResponse(entity);
  }
}
