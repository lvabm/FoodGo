package com.foodgo.backend.module.menu.dto.criteria;

import com.foodgo.backend.module.menu.dto.request.MenuItemFeatureFilterRequest;
import com.foodgo.backend.module.menu.entity.MenuItemFeature;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record MenuItemFeatureSpecification(MenuItemFeatureFilterRequest filter)
    implements Specification<MenuItemFeature> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<MenuItemFeature> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Loại đặc điểm (Chính xác)
    filter
        .optionalFeatureType()
        .ifPresent(
            featureType -> {
              predicates.add(builder.equal(root.get("featureType"), featureType));
            });

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
