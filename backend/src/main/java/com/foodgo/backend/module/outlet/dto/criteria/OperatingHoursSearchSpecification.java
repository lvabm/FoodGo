package com.foodgo.backend.module.outlet.dto.criteria;

import com.foodgo.backend.module.outlet.dto.request.filter.OperatingHoursFilterRequest;
import com.foodgo.backend.module.outlet.entity.OperatingHours;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record OperatingHoursSearchSpecification(OperatingHoursFilterRequest request)
    implements Specification<OperatingHours> {

  @Override
  public Predicate toPredicate(
      Root<OperatingHours> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Outlet ID (cốt lõi)
    request
        .optionalOutletId()
        .ifPresent(
            outletId -> predicates.add(builder.equal(root.get("outlet").get("id"), outletId)));

    // 2. Lọc theo Day of Week
    request
        .optionalDayOfWeek()
        .ifPresent(dayOfWeek -> predicates.add(builder.equal(root.get("dayOfWeek"), dayOfWeek)));

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
