package com.foodgo.backend.facade;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrentUserDataFacade {

  /**
   * Lấy đối tượng UserAccount từ SecurityContext (đã được set trong JWT Filter để tránh truy vấn DB
   * lặp lại)
   */
  private UserAccount getCurrentUser() {
    return SecurityContext.getCurrentUserAccount();
  }

  // ================================
  // 1. ONE-TO-ONE: USER PROFILE
  // ================================

  /** Lấy thông tin Profile của user */
  public Object getMyProfile() {
    return getCurrentUser().getProfile();
  }

  // ================================
  // 2. ONE-TO-MANY: BOOKINGS
  // ================================

  /** Tất cả bookings của user */
  public Object getMyBookings() {
    return getCurrentUser().getBookings();
  }

  // ================================
  // 3. ONE-TO-MANY: REVIEWS
  // ================================

  /** Tất cả reviews user đã tạo */
  public Object getMyReviews() {
    return getCurrentUser().getReviews();
  }

  // ================================
  // 4. ONE-TO-MANY: OUTLETS
  // ================================

  /** Các outlets user sở hữu */
  public Object getMyOutlets() {
    return getCurrentUser().getOutlets();
  }

  // ================================
  // 5. ONE-TO-MANY: REVIEW REPLIES
  // ================================

  /** Các review replies user đã tạo */
  public Object getMyReviewReplies() {
    return getCurrentUser().getReviewReplies();
  }

  // ================================
  // 6. ONE-TO-MANY: REVIEW REACTIONS
  // ================================

  /** Tất cả reaction của user vào các reviews */
  public Object getMyReviewReactions() {
    return getCurrentUser().getReviewReactions();
  }

  // ================================
  // 7. ONE-TO-MANY: REVIEW REPORTS
  // ================================

  /** Danh sách report user đã gửi */
  public Object getMyReviewReports() {
    return getCurrentUser().getReviewReports();
  }

  // ================================
  // 8. ONE-TO-MANY: NOTIFICATIONS
  // ================================

  /** Danh sách thông báo của user */
  public Object getMyNotifications() {
    return getCurrentUser().getNotifications();
  }

  // ================================
  // 9. ONE-TO-MANY: MEMBERSHIPS
  // ================================

  /** Danh sách membership user tham gia */
  public Object getMyMemberships() {
    return getCurrentUser().getUserMemberships();
  }

  // ================================
  // 10. ONE-TO-MANY: SHARING LIST COLLABORATORS
  // ================================

  /** Danh sách sharing list mà user là collaborator */
  public Object getMySharingListCollaborations() {
    return getCurrentUser().getSharingListCollaborators();
  }
}
