package com.foodgo.backend.module.menu.dto.criteria;

import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletMenuItemFilterRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record OutletMenuItemSearchSpecification(OutletMenuItemFilterRequest request)
    implements Specification<OutletMenuItem> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<OutletMenuItem> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên món ăn (tương đối)
    request
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Outlet ID (Many-to-One Join)
    request
        .optionalOutletId()
        .ifPresent(
            outletId -> {
              // Trường tên trong OutletMenuItem Entity là "outlet"
              predicates.add(builder.equal(root.get("outlet").get("id"), outletId));
            });

    // 3. Lọc theo trạng thái Available
    request
        .optionalIsAvailable()
        .ifPresent(
            isAvailable -> {
              predicates.add(builder.equal(root.get("isAvailable"), isAvailable));
            });

    // Query luôn phải distinct nếu có thể có Join (mặc dù hiện tại không Join Many-to-Many)
    if (query != null) {
      query.distinct(true);
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
