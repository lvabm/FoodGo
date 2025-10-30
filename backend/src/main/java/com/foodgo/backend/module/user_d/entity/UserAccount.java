package com.foodgo.backend.module.user_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.booking_d.entity.Booking;
import com.foodgo.backend.module.group_d.entity.UserGroup;
import com.foodgo.backend.module.review_d.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount extends BaseEntity {

  @Column(name = "username", nullable = false, unique = true, length = 30)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @Column(name = "phone_number", unique = true, length = 20)
  private String phoneNumber;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;


  //1. QUAN HỆ ONE - TO - ONE: UserAccount <--> Profile
  // Profile sở hữu quan hệ (fk_user_id_profile)
  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Profile profile;

  //2. QUAN HỆ ONE - TO - MANY: UserAccount <--> UserRole
  // UserRole sở hữu quan hệ (fk_user_id_user_role)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserRole> userRoles;

  //3. QUAN HỆ ONE - TO - MANY: UserAccount <--> Booking
  // Booking sở hữu quan hệ (fk_user_id_booking)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Booking> bookings;

  //4. QUAN HỆ ONE - TO - MANY: UserAccount <--> Review
  // Review sở hữu quan hệ (fk_user_id_review)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Review> reviews;

  //5. QUAN HỆ ONE - TO - MANY: UserAccount <--> PasswordResetToken
  // PasswordResetToken sở hữu quan hệ (fk_user_id_password_reset_token)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<PasswordResetToken> passwordResetTokens;

}
