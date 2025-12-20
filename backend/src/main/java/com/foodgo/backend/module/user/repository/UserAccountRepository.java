package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAccountRepository
    extends JpaRepository<UserAccount, UUID>, JpaSpecificationExecutor<UserAccount> {

  // Tải đồng thời quan hệ Role
  @EntityGraph(attributePaths = {"role"})
  Optional<UserAccount> findByUsername(String username);

  // Tải đồng thời quan hệ Role
  @EntityGraph(attributePaths = {"role"})
  Optional<UserAccount> findByEmail(String email);

  Boolean existsByEmail(String email);

  /// Chỉ dùng findByPasswordHash cho PasswordUpdateRunner
  List<UserAccount> findByPasswordHash(String passwordHash);

  @EntityGraph(attributePaths = {"profile", "profile.country", "role"})
  Optional<UserAccount> findById(UUID id);

  // BOOKINGS
  @EntityGraph(attributePaths = {"bookings"})
  Optional<UserAccount> findWithBookingsById(UUID id);

  // REVIEWS
  @EntityGraph(attributePaths = {"reviews"})
  Optional<UserAccount> findWithReviewsById(UUID id);

  // OUTLETS
  @EntityGraph(attributePaths = {"outlets"})
  Optional<UserAccount> findWithOutletsById(UUID id);

  // REVIEW REPLIES
  @EntityGraph(attributePaths = {"reviewReplies"})
  Optional<UserAccount> findWithReviewRepliesById(UUID id);

  // REVIEW REACTIONS
  @EntityGraph(attributePaths = {"reviewReactions"})
  Optional<UserAccount> findWithReviewReactionsById(UUID id);

  // REVIEW REPORTS
  @EntityGraph(attributePaths = {"reviewReports"})
  Optional<UserAccount> findWithReviewReportsById(UUID id);

  // NOTIFICATIONS
  @EntityGraph(attributePaths = {"notifications"})
  Optional<UserAccount> findWithNotificationsById(UUID id);

  // SHARING LIST COLLABORATIONS
  @EntityGraph(attributePaths = {"sharingListCollaborators"})
  Optional<UserAccount> findWithSharingListCollaboratorsById(UUID id);
}
