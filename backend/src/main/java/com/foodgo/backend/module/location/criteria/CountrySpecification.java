package com.foodgo.backend.module.location.criteria;

import com.foodgo.backend.module.location.dto.CountryFilterRequest;
import com.foodgo.backend.module.location.entity.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record CountrySpecification(CountryFilterRequest filter) implements Specification<Country> {

  @Override
  public Predicate toPredicate(
      Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
