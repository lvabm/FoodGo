package com.foodgo.backend.module.review.dto.request.filter;

import java.util.Optional;
import java.util.UUID;

public record ReviewFilterRequest(UUID outletId, UUID userId, Integer minRating, Boolean hasReply) {

  public Optional<UUID> optionalOutletId() {
    return Optional.ofNullable(outletId);
  }

  public Optional<UUID> optionalUserId() {
    return Optional.ofNullable(userId);
  }

  public Optional<Integer> optionalMinRating() {
    return Optional.ofNullable(minRating);
  }

  public Optional<Boolean> optionalHasReply() {
    return Optional.ofNullable(hasReply);
  }
}
