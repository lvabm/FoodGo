package com.foodgo.backend.module.menu.dto.criteria;

import com.foodgo.backend.module.menu.dto.request.MenuItemTypeFilterRequest;
import com.foodgo.backend.module.menu.entity.MenuItemType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record MenuItemTypeSpecification(MenuItemTypeFilterRequest filter)
    implements Specification<MenuItemType> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<MenuItemType> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
