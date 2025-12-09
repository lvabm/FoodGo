package com.foodgo.backend.module.outlet.dto.criteria;

import com.foodgo.backend.module.outlet.dto.request.OutletFeatureFilterRequest;
import com.foodgo.backend.module.outlet.entity.OutletFeature;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record OutletFeatureSpecification(OutletFeatureFilterRequest filter)
    implements Specification<OutletFeature> {

  @Override
  public Predicate toPredicate(
      Root<OutletFeature> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
