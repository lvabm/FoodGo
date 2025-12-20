package com.foodgo.backend.module.menu.dto.criteria;

import com.foodgo.backend.module.menu.dto.request.MenuItemSubCategoryFilterRequest;
import com.foodgo.backend.module.menu.entity.MenuItemSubCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record MenuItemSubCategorySpecification(MenuItemSubCategoryFilterRequest filter)
    implements Specification<MenuItemSubCategory> {

  @Override
  public Predicate toPredicate(
      Root<MenuItemSubCategory> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Category ID (Lọc theo quan hệ Many-to-One)
    filter
        .optionalCategoryId()
        .ifPresent(
            categoryId -> {
              // Tên trường trong Entity MenuItemSubCategory là "category"
              predicates.add(builder.equal(root.get("category").get("id"), categoryId));
            });

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
