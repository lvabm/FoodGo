package com.foodgo.backend.module.review.dto.criteria;

import com.foodgo.backend.module.review.dto.request.filter.ReviewReportFilterRequest;
import com.foodgo.backend.module.review.entity.ReviewReport;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ReviewReportSpecification(ReviewReportFilterRequest filter)
    implements Specification<ReviewReport> {

  @Override
  public jakarta.persistence.criteria.Predicate toPredicate(
      jakarta.persistence.criteria.Root<ReviewReport> root,
      jakarta.persistence.criteria.CriteriaQuery<?> query,
      jakarta.persistence.criteria.CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    // Fetch join để tránh LazyInitializationException
    if (Long.class != query.getResultType()) {
      root.fetch("reporter", JoinType.LEFT).fetch("profile", JoinType.LEFT);
      root.fetch("review", JoinType.LEFT);
      query.distinct(true);
    }

    filter
        .optionalStatus()
        .ifPresent(status -> predicates.add(cb.equal(root.get("status"), status)));

    filter
        .optionalReviewId()
        .ifPresent(id -> predicates.add(cb.equal(root.get("review").get("id"), id)));

    filter
        .optionalReporterId()
        .ifPresent(id -> predicates.add(cb.equal(root.get("reporter").get("id"), id)));

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}

