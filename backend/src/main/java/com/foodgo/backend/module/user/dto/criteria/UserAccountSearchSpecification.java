package com.foodgo.backend.module.user.dto.criteria;

import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.module.user.dto.request.filter.UserAccountFilterRequest;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public record UserAccountSearchSpecification(UserAccountFilterRequest filter)
    implements Specification<UserAccount> {

  @Override
  public Predicate toPredicate(@NonNull Root<UserAccount> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    // Fetch join để tránh N+1 query khi lấy Role và Profile
    if (query != null && Long.class != query.getResultType()) {
      root.fetch("role", JoinType.LEFT);
      root.fetch("profile", JoinType.LEFT);
      query.distinct(true);
    }

    if (filter != null) {
      // Filter: Active Status
      if (filter.isActive() != null) {
        predicates.add(cb.equal(root.get("isActive"), filter.isActive()));
      }

      // Filter: Role Name (Enum)
      if (StringUtils.hasText(filter.roleName())) {
        try {
          // Convert String -> Enum để so sánh chính xác
          RoleType roleType = RoleType.valueOf(filter.roleName().toUpperCase());
          predicates.add(cb.equal(root.get("role").get("name"), roleType));
        } catch (IllegalArgumentException e) {
          // Nếu role name sai, trả về false (không tìm thấy)
          predicates.add(cb.disjunction());
        }
      }

      // Filter: Search Term (Email or FullName)
      if (StringUtils.hasText(filter.searchTerm())) {
        String pattern = "%" + filter.searchTerm().toLowerCase() + "%";
        // Search in email and profile.fullName (handle null profile safely)
        var emailPredicate = cb.like(cb.lower(root.get("email")), pattern);
        var profileJoin = root.join("profile", JoinType.LEFT);
        var fullNamePredicate = cb.like(cb.lower(profileJoin.get("fullName")), pattern);
        predicates.add(cb.or(emailPredicate, fullNamePredicate));
      }
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
