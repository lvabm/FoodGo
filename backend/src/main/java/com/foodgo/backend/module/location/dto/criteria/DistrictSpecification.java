package com.foodgo.backend.module.location.dto.criteria;

import com.foodgo.backend.module.location.dto.request.DistrictFilterRequest;
import com.foodgo.backend.module.location.entity.District;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record DistrictSpecification(DistrictFilterRequest filter)
    implements Specification<District> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<District> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Province ID (Lọc theo quan hệ Many-to-One)
    filter
        .optionalProvinceId()
        .ifPresent(
            provinceId -> {
              // Tên trường trong Entity District là "province"
              predicates.add(builder.equal(root.get("province").get("id"), provinceId));
            });

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
