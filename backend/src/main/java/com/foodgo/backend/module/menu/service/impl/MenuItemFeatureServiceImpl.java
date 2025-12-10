package com.foodgo.backend.module.menu.service.impl;

import com.foodgo.backend.common.base.service.ReadOnlyServiceImpl;
import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.menu.dto.criteria.MenuItemFeatureSpecification;
import com.foodgo.backend.module.menu.dto.mapper.MenuItemFeatureMapper;
import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureFilterRequest;
import com.foodgo.backend.module.menu.dto.response.MenuItemFeatureResponse;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import com.foodgo.backend.module.menu.repository.MenuItemFeatureRepository;
import com.foodgo.backend.module.menu.service.MenuItemFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemFeatureServiceImpl
    extends ReadOnlyServiceImpl<
        MenuItemFeature, MenuItemFeatureResponse, Integer, MenuItemFeatureFilterRequest>
    implements MenuItemFeatureService {

  private final String menuItemFeatureEntityName = EntityName.MENU_ITEM_FEATURE.getFriendlyName();
  private final MenuItemFeatureRepository repository;
  private final MenuItemFeatureMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ ReadOnlyServiceImpl ---

  @Override
  protected JpaRepository<MenuItemFeature, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<MenuItemFeature> getSpecRepository() {
    return repository;
  }

  @Override
  protected ReadableMapper<MenuItemFeature, MenuItemFeatureResponse> getMapper() {
    return mapper;
  }

  @Override
  protected String getEntityName() {
    return menuItemFeatureEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<MenuItemFeature> buildSpecification(MenuItemFeatureFilterRequest filter) {
    return new MenuItemFeatureSpecification(filter);
  }
}
