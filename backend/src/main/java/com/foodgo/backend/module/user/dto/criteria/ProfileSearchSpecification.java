package com.foodgo.backend.module.user.dto.criteria;

import com.foodgo.backend.module.user.dto.request.filter.ProfileFilterRequest;
import com.foodgo.backend.module.user.entity.Profile;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public record ProfileSearchSpecification(ProfileFilterRequest request)
    implements Specification<Profile> {

  @Override
  public Predicate toPredicate(
      @NonNull Root<Profile> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên đầy đủ (tương đối)
    request
        .optionalFullName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(
                        builder.lower(root.get("fullName")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo User ID (chỉ Admin)
    request
        .optionalUserId()
        .ifPresent(
            userId -> {
              predicates.add(builder.equal(root.get("userAccount").get("id"), userId));
            });

    // 3. Lọc theo Country ID
    request
        .optionalCountryId()
        .ifPresent(
            countryId -> {
              predicates.add(builder.equal(root.get("country").get("id"), countryId));
            });

    if (query != null) {
      query.distinct(true);
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
