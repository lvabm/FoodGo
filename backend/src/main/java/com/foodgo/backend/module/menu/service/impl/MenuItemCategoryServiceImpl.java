package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.menu.dto.criteria.MenuItemCategorySpecification;
import com.foodgo.backend.module.menu.dto.mapper.MenuItemCategoryMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemCategoryResponse;
import com.foodgo.backend.module.menu.entity.MenuItemCategory;
import com.foodgo.backend.module.menu.repository.MenuItemCategoryRepository;
import com.foodgo.backend.module.menu.service.MenuItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemCategoryServiceImpl
    extends BaseServiceImpl<
        MenuItemCategory,
        Integer,
        Object,
        Object,
        MenuItemCategoryFilterRequest,
        MenuItemCategoryResponse>
    implements MenuItemCategoryService {

  private final MenuItemCategoryRepository repository;
  private final MenuItemCategoryMapper mapper;

  private final String menuItemCategoryEntityName = EntityName.MENU_ITEM_CATEGORY.getFriendlyName();

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<MenuItemCategory, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<MenuItemCategory> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<MenuItemCategory, Object, Object, MenuItemCategoryResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return menuItemCategoryEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<MenuItemCategory> buildSpecification(
      MenuItemCategoryFilterRequest filter) {
    return new MenuItemCategorySpecification(filter);
  }
}
