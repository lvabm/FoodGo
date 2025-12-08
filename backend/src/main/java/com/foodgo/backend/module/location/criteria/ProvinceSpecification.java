package com.foodgo.backend.module.location.criteria;

import com.foodgo.backend.module.location.dto.ProvinceFilterRequest;
import com.foodgo.backend.module.location.entity.Province;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ProvinceSpecification(ProvinceFilterRequest filter)
    implements Specification<Province> {

  @Override
  public Predicate toPredicate(
      Root<Province> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối, không phân biệt chữ hoa/thường)
    filter
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Country ID (Lọc theo quan hệ Many-to-One)
    filter
        .optionalCountryId()
        .ifPresent(
            countryId -> {
              // Tên trường trong Entity Province là "country"
              predicates.add(builder.equal(root.get("country").get("id"), countryId));
            });

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
