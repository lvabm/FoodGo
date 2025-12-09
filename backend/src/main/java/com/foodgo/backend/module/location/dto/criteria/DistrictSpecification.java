package com.foodgo.backend.module.location.dto.criteria;

import com.foodgo.backend.module.location.dto.request.DistrictFilterRequest;
import com.foodgo.backend.module.location.entity.District;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record DistrictSpecification(DistrictFilterRequest filter)
    implements Specification<District> {

  @Override
  public Predicate toPredicate(
      Root<District> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
