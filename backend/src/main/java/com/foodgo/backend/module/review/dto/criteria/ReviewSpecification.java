package com.foodgo.backend.module.review.dto.criteria;

import com.foodgo.backend.module.review.dto.request.filter.ReviewFilterRequest;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.entity.ReviewReply;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record ReviewSpecification(ReviewFilterRequest filter) implements Specification<Review> {

  @Override
  public Predicate toPredicate(@NonNull Root<Review> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    // Fetch join để tránh LazyInitializationException khi map ReviewResponse
    if (query != null && Long.class != query.getResultType()) {
      root.fetch("outlet", JoinType.LEFT);
      root.fetch("user", JoinType.LEFT).fetch("profile", JoinType.LEFT);
      root.fetch("booking", JoinType.LEFT);
      // Fetch reviewReply và owner.profile để hiển thị phản hồi của chủ quán
      Fetch<Review, ReviewReply> replyFetch = root.fetch("reviewReply", JoinType.LEFT);
      replyFetch.fetch("owner", JoinType.LEFT).fetch("profile", JoinType.LEFT);
      query.distinct(true);
    }

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
