package com.foodgo.backend.module.outlet.criteria;

import com.foodgo.backend.module.outlet.dto.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.entity.OutletType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record OutletTypeSpecification(OutletTypeFilterRequest filter)
    implements Specification<OutletType> {

  @Override
  public Predicate toPredicate(
      Root<OutletType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
