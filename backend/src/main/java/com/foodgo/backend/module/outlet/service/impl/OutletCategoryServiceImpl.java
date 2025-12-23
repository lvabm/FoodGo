package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.module.outlet.dto.criteria.OutletCategorySearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletCategoryMapper;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletCategoryResponse;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import com.foodgo.backend.module.outlet.repository.OutletCategoryRepository;
import com.foodgo.backend.module.outlet.service.OutletCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutletCategoryServiceImpl
    extends BaseServiceImpl<
        OutletCategory,
        Integer,
        Object,
        Object,
        OutletCategoryFilterRequest,
        OutletCategoryResponse>
    implements OutletCategoryService {

  private final String outletCategoryEntityName = EntityName.OUTLET_CATEGORY.getFriendlyName();
  private final OutletCategoryRepository repository;
  private final OutletCategoryMapper mapper;

  // --- Triển khai các phương thức trừu tượng từ BaseServiceImpl ---

  @Override
  protected JpaRepository<OutletCategory, Integer> getRepository() {
    return repository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletCategory> getSpecRepository() {
    return repository;
  }

  @Override
  protected BaseMapper<OutletCategory, Object, Object, OutletCategoryResponse> getMapper() {
    // Wrapper để adapt OutletCategoryMapper (với CreateRequest/UpdateRequest) 
    // thành BaseMapper với Object, Object cho ReadableService
    return new BaseMapper<OutletCategory, Object, Object, OutletCategoryResponse>() {
      @Override
      public OutletCategoryResponse toResponse(OutletCategory entity) {
        return mapper.toResponse(entity);
      }

      @Override
      public java.util.List<OutletCategoryResponse> toResponseList(
          java.util.List<OutletCategory> entities) {
        return mapper.toResponseList(entities);
      }

      @Override
      public OutletCategory toEntity(Object createRequest) {
        throw new UnsupportedOperationException(
            "OutletCategoryServiceImpl chỉ hỗ trợ Read operations");
      }

      @Override
      public void updateEntity(Object updateRequest, OutletCategory entity) {
        throw new UnsupportedOperationException(
            "OutletCategoryServiceImpl chỉ hỗ trợ Read operations");
      }
    };
  }

  @Override
  protected String getEntityName() {
    return outletCategoryEntityName;
  }

  // Triển khai logic Specification
  @Override
  protected Specification<OutletCategory> buildSpecification(OutletCategoryFilterRequest filter) {
    return new OutletCategorySearchSpecification(filter);
  }
}
