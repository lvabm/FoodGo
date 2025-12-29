package com.foodgo.backend.module.menu.dto.criteria;

import com.foodgo.backend.module.menu.dto.request.MenuItemFilterRequest;
import com.foodgo.backend.module.menu.entity.MenuItem;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record MenuItemSearchSpecification(MenuItemFilterRequest request)
    implements Specification<MenuItem> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<MenuItem> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    request
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    request
        .optionalIsPopular()
        .ifPresent(isPopular -> predicates.add(builder.equal(root.get("isPopular"), isPopular)));

    // Lọc theo SubCategory ID (Join)
    request
        .optionalSubCategoryId()
        .ifPresent(
            subCategoryId -> {
              predicates.add(builder.equal(root.get("subCategory").get("id"), subCategoryId));
            });

    // Lọc theo Province ID (Join)
    request
        .optionalProvinceId()
        .ifPresent(
            provinceId -> {
              predicates.add(builder.equal(root.get("province").get("id"), provinceId));
            });

    if (query != null) {
      query.distinct(true);
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
