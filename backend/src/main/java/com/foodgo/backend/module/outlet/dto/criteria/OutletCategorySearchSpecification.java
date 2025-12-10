package com.foodgo.backend.module.outlet.dto.criteria;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletCategoryFilterRequest;
import com.foodgo.backend.module.outlet.entity.OutletCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record OutletCategorySearchSpecification(OutletCategoryFilterRequest filter)
    implements Specification<OutletCategory> {

  @Override
  public Predicate toPredicate(
      Root<OutletCategory> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Type ID (Lọc theo quan hệ Many-to-One)
    filter
        .optionalTypeId()
        .ifPresent(
            typeId -> {
              // Tên trường trong Entity OutletCategory là "type"
              predicates.add(builder.equal(root.get("type").get("id"), typeId));
            });

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
