package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminMenuItemService;
import com.foodgo.backend.module.location.repository.ProvinceRepository;
import com.foodgo.backend.module.menu.dto.criteria.MenuItemSearchSpecification;
import com.foodgo.backend.module.menu.dto.mapper.MenuItemMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemCreateRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.dto.request.MenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemResponse;
import com.foodgo.backend.module.menu.entity.MenuItem;
import com.foodgo.backend.module.menu.repository.MenuItemRepository;
import com.foodgo.backend.module.menu.repository.MenuItemSubCategoryRepository;
import com.foodgo.backend.module.menu.repository.OutletMenuItemRepository;
import com.foodgo.backend.module.menu.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuItemServiceImpl
    extends BaseServiceImpl<
        MenuItem,
        UUID,
        MenuItemCreateRequest,
        MenuItemUpdateRequest,
        MenuItemFilterRequest,
        MenuItemResponse>
    implements MenuItemService, AdminMenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final MenuItemMapper menuItemMapper;
  private final ProvinceRepository provinceRepository;
  private final MenuItemSubCategoryRepository subCategoryRepository;
  private final OutletMenuItemRepository outletMenuItemRepository;

  private final String menuItemEntityName = EntityName.MENU_ITEM.getFriendlyName();

  // --- Abstract Methods ---
  @Override
  protected JpaRepository<MenuItem, UUID> getRepository() {
    return menuItemRepository;
  }

  @Override
  protected JpaSpecificationExecutor<MenuItem> getSpecRepository() {
    return menuItemRepository;
  }

  @Override
  protected BaseMapper<MenuItem, MenuItemCreateRequest, MenuItemUpdateRequest, MenuItemResponse>
      getMapper() {
    return menuItemMapper;
  }

  @Override
  protected String getEntityName() {
    return menuItemEntityName;
  }

  // --- Hooks & Specification ---

  /** 🔑 HOOK: Kiểm tra quyền Admin cho các thao tác ghi (CRUD) */
  @Override
  protected void ensurePermission(MenuItem entity) {
    // HARD RULE: MenuItem là dữ liệu chung, chỉ Admin mới được phép CRUD
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException(
          "Chỉ Admin mới có quyền thao tác với " + getEntityName() + ".");
    }
  }

  @Override
  protected Specification<MenuItem> buildSpecification(MenuItemFilterRequest filterRequest) {
    return new MenuItemSearchSpecification(filterRequest);
  }

  @Override
  @Transactional(readOnly = true)
  public String getMenuItemImageUrl(UUID menuItemId) {
    log.info("🔍 Getting image URL for menu item: {}", menuItemId);
    
    try {
      // Tìm outlet menu items có sử dụng menu item này và có ảnh (giới hạn 5 items)
      java.util.List<com.foodgo.backend.module.menu.entity.OutletMenuItem> outletMenuItems = 
          outletMenuItemRepository.findTop5ByMenuItemIdAndImageUrlIsNotNullOrderByIdAsc(menuItemId);
      
      log.info("📸 Found {} outlet menu items with images for menu item {}", outletMenuItems.size(), menuItemId);
      
      // Lấy ảnh đầu tiên nếu có
      if (!outletMenuItems.isEmpty()) {
        String imageUrl = outletMenuItems.get(0).getImageUrl();
        log.info("✅ Returning image URL: {}", imageUrl);
        return imageUrl;
      }
      
      log.info("⚠️ No image found for menu item {}", menuItemId);
      return null;
    } catch (Exception e) {
      log.error("❌ Error getting image URL for menu item {}: {}", menuItemId, e.getMessage(), e);
      e.printStackTrace();
      return null;
    }
  }

  // --- Ghi đè CREATE/UPDATE để gán FK ---

  @Override
  @Transactional
  public MenuItemResponse create(MenuItemCreateRequest request) {
    ensurePermission(null); // Kiểm tra quyền Admin trước khi tạo

    // 1. Validate FK tồn tại
    if (!provinceRepository.existsById(request.provinceId())) {
      throw new ResourceNotFoundException("Province" + " id: " + request.provinceId());
    }
    if (!subCategoryRepository.existsById(request.subCategoryId())) {
      throw new ResourceNotFoundException(
          "MenuItemSubCategory" + " id: " + request.subCategoryId());
    }

    // 2. Mapping và gán Entity
    MenuItem entity = menuItemMapper.toEntity(request);
    entity.setProvince(provinceRepository.getReferenceById(request.provinceId()));
    entity.setSubCategory(subCategoryRepository.getReferenceById(request.subCategoryId()));

    // 3. Lưu và hoàn tất (dùng Base Logic để set message)
    MenuItem savedEntity = menuItemRepository.save(entity);
    afterCreate(savedEntity);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));

    return menuItemMapper.toResponse(savedEntity);
  }

  @Override
  @Transactional
  public MenuItemResponse update(UUID id, MenuItemUpdateRequest request) {
    // 1. Kiểm tra quyền sở hữu (Gọi ensurePermission)
    MenuItem entity = findByIdOrThrow(id);
    ensurePermission(entity);

    // 2. Validation và GÁN Entity FK nếu được cung cấp (Partial Update)
    request
        .optionalProvinceId()
        .ifPresent(
            provinceId -> {
              if (!provinceRepository.existsById(provinceId)) {
                throw new ResourceNotFoundException("Province" + " id: " + provinceId);
              }
              entity.setProvince(provinceRepository.getReferenceById(provinceId));
            });

    request
        .optionalSubCategoryId()
        .ifPresent(
            subCategoryId -> {
              if (!subCategoryRepository.existsById(subCategoryId)) {
                throw new ResourceNotFoundException(
                    "MenuItemSubCategory" + " id: " + subCategoryId);
              }
              entity.setSubCategory(subCategoryRepository.getReferenceById(subCategoryId));
            });

    // 3. Dùng MapStruct cho các trường còn lại
    menuItemMapper.updateEntity(request, entity);

    // 4. Lưu và hoàn tất
    MenuItem updatedEntity = getRepository().save(entity);
    afterUpdate(updatedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return menuItemMapper.toResponse(updatedEntity);
  }
}
