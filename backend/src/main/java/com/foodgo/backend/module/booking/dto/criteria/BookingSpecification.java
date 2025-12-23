package com.foodgo.backend.module.booking.dto.criteria;

import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.booking.dto.request.filter.BookingFilterRequest;
import com.foodgo.backend.module.booking.entity.Booking;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record BookingSpecification(BookingFilterRequest filter) implements Specification<Booking> {

  @Override
  public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. SECURITY FILTER
    if (!SecurityContext.isAdmin()) {
      UUID currentUserId = SecurityContext.getCurrentUserId();
      if (SecurityContext.hasRole("ROLE_OWNER")) {
        // Check viewType to determine which bookings to show
        String viewType = filter.optionalViewType().orElse("MY_BOOKINGS");
        
        if ("MANAGE_BOOKINGS".equals(viewType)) {
          // Owner management view: show bookings made by OTHER users at owner's outlets
          predicates.add(
              cb.and(
                  cb.equal(root.get("outlet").get("owner").get("id"), currentUserId),
                  cb.notEqual(root.get("user").get("id"), currentUserId)));
        } else {
          // Owner personal view (MY_BOOKINGS): show owner's own bookings at OTHER outlets
          predicates.add(cb.equal(root.get("user").get("id"), currentUserId));
        }
      } else {
        // Regular users: only see their own bookings
        predicates.add(cb.equal(root.get("user").get("id"), currentUserId));
      }
    } else {
      // Admin filter
      filter
          .optionalUserId()
          .ifPresent(uid -> predicates.add(cb.equal(root.get("user").get("id"), uid)));
    }

    // 2. BUSINESS FILTER (Functional Style)

    filter
        .optionalOutletId()
        .ifPresent(oid -> predicates.add(cb.equal(root.get("outlet").get("id"), oid)));

    filter
        .optionalStatus()
        .ifPresent(
            statusStr -> {
              try {
                predicates.add(cb.equal(root.get("status"), BookingStatus.valueOf(statusStr)));
              } catch (IllegalArgumentException ignored) {
              }
            });

    filter
        .optionalDateFrom()
        .ifPresent(date -> predicates.add(cb.greaterThanOrEqualTo(root.get("bookingDate"), date)));

    filter
        .optionalDateTo()
        .ifPresent(date -> predicates.add(cb.lessThanOrEqualTo(root.get("bookingDate"), date)));

    filter
        .optionalSearchTerm()
        .ifPresent(
            term -> {
              String pattern = "%" + term.toLowerCase() + "%";
              predicates.add(
                  cb.or(
                      cb.like(cb.lower(root.get("user").get("profile").get("fullName")), pattern),
                      cb.like(cb.lower(root.get("outlet").get("name")), pattern)));
            });

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
