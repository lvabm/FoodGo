package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.service.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.menu.dto.criteria.MenuItemSubCategorySpecification;
import com.foodgo.backend.module.menu.dto.mapper.MenuItemSubCategoryMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemSubCategoryResponse;
import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import com.foodgo.backend.module.menu.repository.MenuItemSubCategoryRepository;
import com.foodgo.backend.module.menu.service.MenuItemSubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemSubCategoryServiceImpl
    extends ReadOnlyServiceImpl<
        MenuItemSubCategory, MenuItemSubCategoryResponse, Integer, MenuItemSubCategoryFilterRequest>
    implements MenuItemSubCategoryService {

  private final String menuItemSubCategoryEntityName =
      EntityName.MENU_ITEM_SUB_CATEGORY.getFriendlyName();
  private final MenuItemSubCategoryRepository repository;
  private final MenuItemSubCategoryMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<MenuItemSubCategory, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<MenuItemSubCategory> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<MenuItemSubCategory, MenuItemSubCategoryResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return menuItemSubCategoryEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<MenuItemSubCategory> buildSpecification(
      MenuItemSubCategoryFilterRequest filter) {
    return new MenuItemSubCategorySpecification(filter);
  }
}
