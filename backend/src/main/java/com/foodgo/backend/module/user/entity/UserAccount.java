package com.foodgo.backend.module.user.entity;

import com.foodgo.backend.common.base.BaseUUIDEntity;
import com.foodgo.backend.module.auth.entity.RefreshToken;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.sharing.entity.SharingListCollaborator;
import com.foodgo.backend.module.membership.entity.UserMembership;
import com.foodgo.backend.module.notification.entity.Notification;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.entity.ReviewReaction;
import com.foodgo.backend.module.review.entity.ReviewReply;
import com.foodgo.backend.module.review.entity.ReviewReport;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount extends BaseUUIDEntity implements UserDetails {

  @Column(name = "username", nullable = false, unique = true, length = 30)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(name = "is_active", nullable = false)
  @Builder.Default
  private boolean isActive = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getName()));
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  // 1. QUAN HỆ ONE - TO - ONE: UserAccount <--> Profile
  // Profile sở hữu quan hệ (fk_user_account_id_profile)
  @OneToOne(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Profile profile;

  // 2. QUAN HỆ ONE - TO - MANY: UserAccount <--> Booking
  // Booking sở hữu quan hệ (fk_user_account_id_booking)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Booking> bookings;

  // 3. QUAN HỆ ONE - TO - MANY: UserAccount <--> Review
  // Review sở hữu quan hệ (fk_user_account_id_review)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Review> reviews;

  // 4. QUAN HỆ ONE - TO - MANY: UserAccount <--> PasswordResetToken
  // PasswordResetToken sở hữu quan hệ (fk_user_account_id_password_reset_token)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<PasswordResetToken> passwordResetTokens;

  // 5. QUAN HỆ ONE - TO - MANY: UserAccount <--> RefreshToken
  // RefreshToken sở hữu quan hệ (fk_user_account_id_refresh_token)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<RefreshToken> refreshTokens;

  // 6. QUAN HỆ ONE - TO - MANY: UserAccount <--> Outlet
  // Outlet sở hữu quan hệ (fk_user_account_id_outlet)
  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Outlet> outlets;

  // 7. QUAN HỆ ONE - TO - MANY: UserAccount <--> ReviewReply
  // ReviewReply sở hữu quan hệ (fk_user_account_id_review_reply)
  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReply> reviewReplies;

  // 8. QUAN HỆ ONE - TO - MANY: UserAccount <--> ReviewReaction
  // ReviewReaction sở hữu quan hệ (fk_user_account_id_review_reaction)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReaction> reviewReactions;

  // 9. QUAN HỆ ONE - TO - MANY: UserAccount <--> ReviewReport
  // ReviewReport sở hữu quan hệ (fk_user_account_id_review_report)
  @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ReviewReport> reviewReports;

  // 10. QUAN HỆ ONE - TO - MANY: UserAccount <--> Notification
  // Notification sở hữu quan hệ (fk_user_account_id_notification)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Notification> notifications;

  // 11. QUAN HỆ ONE - TO - MANY: UserAccount <--> UserMembership
  // UserMembership sở hữu quan hệ (fk_user_account_id_user_membership)
  @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserMembership> userMemberships;

  // 12. QUAN HỆ ONE - TO - MANY: UserAccount <--> SharingListCollaborator
  // SharingListCollaborator sở hữu quan hệ
  // (fk_user_account_id_sharing_list_collaborator)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<SharingListCollaborator> sharingListCollaborators;
}
