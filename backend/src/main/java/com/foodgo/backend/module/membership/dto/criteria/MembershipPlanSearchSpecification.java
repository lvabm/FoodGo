package com.foodgo.backend.module.membership.dto.criteria;

import com.foodgo.backend.module.membership.dto.request.filter.MembershipPlanFilterRequest;
import com.foodgo.backend.module.membership.entity.MembershipPlan;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record MembershipPlanSearchSpecification(MembershipPlanFilterRequest filter)
    implements Specification<MembershipPlan> {

  @Override
  public Predicate toPredicate(
      Root<MembershipPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Filter by Keyword (Name OR Description)
    filter
        .getKeywordOpt()
        .ifPresent(
            keyword -> {
              String likePattern = "%" + keyword.toLowerCase() + "%";
              predicates.add(
                  cb.or(
                      cb.like(cb.lower(root.get("name")), likePattern),
                      cb.like(cb.lower(root.get("description")), likePattern)));
            });

    // 2. Filter by Type
    filter.getTypeOpt().ifPresent(type -> predicates.add(cb.equal(root.get("type"), type)));

    // 3. Filter by Price Range
    filter
        .getMinPriceOpt()
        .ifPresent(min -> predicates.add(cb.greaterThanOrEqualTo(root.get("price"), min)));

    filter
        .getMaxPriceOpt()
        .ifPresent(max -> predicates.add(cb.lessThanOrEqualTo(root.get("price"), max)));

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
