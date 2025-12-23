package com.foodgo.backend.module.review.dto.criteria;

import com.foodgo.backend.module.review.dto.request.filter.ReviewFilterRequest;
import com.foodgo.backend.module.review.entity.Review;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ReviewSpecification(ReviewFilterRequest filter) implements Specification<Review> {

  @Override
  public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    filter
        .optionalOutletId()
        .ifPresent(id -> predicates.add(cb.equal(root.get("outlet").get("id"), id)));

    filter
        .optionalUserId()
        .ifPresent(id -> predicates.add(cb.equal(root.get("user").get("id"), id)));

    filter
        .optionalMinRating()
        .ifPresent(
            rating -> predicates.add(cb.greaterThanOrEqualTo(root.get("overallRating"), rating)));

    filter
        .optionalHasReply()
        .ifPresent(
            hasReply -> {
              if (hasReply) predicates.add(cb.isNotNull(root.get("reviewReply")));
              else predicates.add(cb.isNull(root.get("reviewReply")));
            });

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
