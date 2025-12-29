package com.foodgo.backend.facade;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.notification.entity.Notification;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.entity.ReviewReaction;
import com.foodgo.backend.module.review.entity.ReviewReply;
import com.foodgo.backend.module.review.entity.ReviewReport;
import com.foodgo.backend.module.sharing.entity.SharingListCollaborator;
import com.foodgo.backend.module.user.entity.Profile;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.ProfileRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserDataFacade {

  private final UserAccountRepository userAccountRepository;
  private final ProfileRepository profileRepository;

  private UUID getUserId() {
    return SecurityContext.getCurrentUserId();
  }

  // ============================================================
  // USER ACCOUNT (WITH profile + country + role)
  // ============================================================

  public UserAccount getCurrentUser() {
    return userAccountRepository
        .findById(getUserId())
        .orElseThrow(() -> new RuntimeException("Không tìm thấy User"));
  }

  // ============================================================
  // PROFILE (WITH Country)
  // ============================================================

  public Profile getMyProfile() {
    return profileRepository.findByUserAccountId(getUserId()).orElse(null);
  }

  // ============================================================
  // BOOKINGS
  // ============================================================

  public Set<Booking> getMyBookings() {
    return userAccountRepository
        .findWithBookingsById(getUserId())
        .map(UserAccount::getBookings)
        .orElse(Set.of());
  }

  // ============================================================
  // REVIEWS
  // ============================================================

  public Set<Review> getMyReviews() {
    return userAccountRepository
        .findWithReviewsById(getUserId())
        .map(UserAccount::getReviews)
        .orElse(Set.of());
  }

  // ============================================================
  // OUTLETS
  // ============================================================

  public Set<Outlet> getMyOutlets() {
    return userAccountRepository
        .findWithOutletsById(getUserId())
        .map(UserAccount::getOutlets)
        .orElse(Set.of());
  }

  // ============================================================
  // REVIEW REPLIES
  // ============================================================

  public Set<ReviewReply> getMyReviewReplies() {
    return userAccountRepository
        .findWithReviewRepliesById(getUserId())
        .map(UserAccount::getReviewReplies)
        .orElse(Set.of());
  }

  // ============================================================
  // REVIEW REACTIONS
  // ============================================================

  public Set<ReviewReaction> getMyReviewReactions() {
    return userAccountRepository
        .findWithReviewReactionsById(getUserId())
        .map(UserAccount::getReviewReactions)
        .orElse(Set.of());
  }

  // ============================================================
  // REVIEW REPORTS
  // ============================================================

  public Set<ReviewReport> getMyReviewReports() {
    return userAccountRepository
        .findWithReviewReportsById(getUserId())
        .map(UserAccount::getReviewReports)
        .orElse(Set.of());
  }

  // ============================================================
  // NOTIFICATIONS
  // ============================================================

  public Set<Notification> getMyNotifications() {
    return userAccountRepository
        .findWithNotificationsById(getUserId())
        .map(UserAccount::getNotifications)
        .orElse(Set.of());
  }

  // ============================================================
  // SHARING LIST COLLABORATOR
  // ============================================================

  public Set<SharingListCollaborator> getMySharingListCollaborations() {
    return userAccountRepository
        .findWithSharingListCollaboratorsById(getUserId())
        .map(UserAccount::getSharingListCollaborators)
        .orElse(Set.of());
  }
}
