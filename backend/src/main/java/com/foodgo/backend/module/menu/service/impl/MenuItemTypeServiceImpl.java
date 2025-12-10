package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.menu.dto.criteria.MenuItemTypeSpecification;
import com.foodgo.backend.module.menu.dto.mapper.MenuItemTypeMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemTypeFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemTypeResponse;
import com.foodgo.backend.module.menu.entity.MenuItemType;
import com.foodgo.backend.module.menu.repository.MenuItemTypeRepository;
import com.foodgo.backend.module.menu.service.MenuItemTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemTypeServiceImpl
    extends ReadOnlyServiceImpl<
        MenuItemType, MenuItemTypeResponse, Integer, MenuItemTypeFilterRequest>
    implements MenuItemTypeService {

  private final MenuItemTypeRepository repository;
  private final MenuItemTypeMapper mapper;

  private final String menuItemTypeEntityName = EntityName.MENU_ITEM_TYPE.getFriendlyName();

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<MenuItemType, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<MenuItemType> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<MenuItemType, MenuItemTypeResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return menuItemTypeEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<MenuItemType> buildSpecification(MenuItemTypeFilterRequest filter) {
    return new MenuItemTypeSpecification(filter);
  }
}
