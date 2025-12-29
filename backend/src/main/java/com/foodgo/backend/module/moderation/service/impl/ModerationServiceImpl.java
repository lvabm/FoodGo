package com.foodgo.backend.module.moderation.service.impl;

import com.foodgo.backend.common.constant.ModerationStatus;
import com.foodgo.backend.common.constant.ReportStatus;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.moderation.service.ModerationService;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.entity.ReviewReport;
import com.foodgo.backend.module.review.repository.ReviewRepository;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation của ModerationService với các quy tắc nghiệp vụ
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModerationServiceImpl implements ModerationService {

  private final ReviewRepository reviewRepository;
  private final UserAccountRepository userAccountRepository;

  // Cấu hình từ application.yml
  @Value("${app.moderation.auto-approve-enabled:true}")
  private boolean autoApproveEnabled;

  @Value("${app.moderation.auto-hide-threshold:3}")
  private int autoHideThreshold; // Số lượng báo cáo để tự động ẩn

  @Value("${app.moderation.spam-check-enabled:true}")
  private boolean spamCheckEnabled;

  @Value("${app.moderation.profanity-check-enabled:true}")
  private boolean profanityCheckEnabled;

  // Danh sách từ ngữ không phù hợp (có thể load từ database hoặc file)
  private static final List<String> PROFANITY_WORDS = List.of(
      "đồ ngu", "ngu si", "đồ chó", "đồ khùng", "đồ điên",
      "fuck", "shit", "damn", "bitch", "asshole"
  );

  // Pattern để phát hiện spam (nhiều ký tự lặp lại)
  private static final Pattern SPAM_PATTERN = Pattern.compile("(.)\\1{10,}");

  @Override
  @Transactional
  public boolean autoModerateReview(Review review) {
    if (!autoApproveEnabled) {
      log.debug("Auto-moderation is disabled, review {} requires manual approval", review.getId());
      return false;
    }

    // Kiểm tra vi phạm nội dung
    if (profanityCheckEnabled && containsViolation(review.getComment())) {
      log.warn("Review {} contains violations, requires moderation", review.getId());
      return false;
    }

    // Kiểm tra spam
    if (spamCheckEnabled && isSpam(review)) {
      log.warn("Review {} detected as spam, requires moderation", review.getId());
      return false;
    }

    // Nếu không vi phạm, tự động duyệt
    review.setModerationStatus(ModerationStatus.AUTO_APPROVED);
    review.setModeratedAt(LocalDateTime.now());
    reviewRepository.save(review);
    
    log.info("Review {} auto-approved", review.getId());
    return true;
  }

  @Override
  public boolean containsViolation(String content) {
    if (content == null || content.isBlank()) {
      return false;
    }

    String lowerContent = content.toLowerCase();
    
    // Kiểm tra từ ngữ không phù hợp
    for (String word : PROFANITY_WORDS) {
      if (lowerContent.contains(word.toLowerCase())) {
        return true;
      }
    }

    // Kiểm tra URL spam (nhiều URL trong một comment)
    long urlCount = content.split("http[s]?://").length - 1;
    if (urlCount > 2) {
      return true;
    }

    // Kiểm tra ký tự đặc biệt quá nhiều (có thể là spam)
    long specialCharCount = content.chars()
        .filter(ch -> !Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch))
        .count();
    if (specialCharCount > content.length() * 0.5) {
      return true;
    }

    return false;
  }

  @Override
  public boolean isSpam(Review review) {
    if (!spamCheckEnabled) {
      return false;
    }

    String comment = review.getComment();
    if (comment == null || comment.isBlank()) {
      return false;
    }

    // Kiểm tra ký tự lặp lại (spam pattern)
    if (SPAM_PATTERN.matcher(comment).find()) {
      return true;
    }

    // Kiểm tra comment quá ngắn (có thể là spam)
    if (comment.trim().length() < 5 && review.getOverallRating() == 5) {
      return true;
    }

    // Kiểm tra user có gửi quá nhiều review trong thời gian ngắn
    LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
    long recentReviews = reviewRepository.countByUserIdAndCreateAtAfter(
        review.getUser().getId(), oneHourAgo);
    
    if (recentReviews > 5) {
      log.warn("User {} has {} reviews in the last hour, possible spam", 
          review.getUser().getId(), recentReviews);
      return true;
    }

    return false;
  }

  @Override
  @Transactional
  public boolean shouldAutoHide(Review review) {
    // Đếm số lượng báo cáo đang chờ xử lý hoặc đã được chấp nhận
    List<ReviewReport> reports = review.getReviewReports();
    if (reports == null || reports.isEmpty()) {
      return false;
    }

    long pendingOrResolvedReports = reports.stream()
        .filter(r -> r.getStatus() == ReportStatus.PENDING || 
                     r.getStatus() == ReportStatus.RESOLVED)
        .count();

    if (pendingOrResolvedReports >= autoHideThreshold) {
      log.warn("Review {} has {} reports, auto-hiding", review.getId(), pendingOrResolvedReports);
      hideReview(review, String.format("Tự động ẩn do có %d báo cáo", pendingOrResolvedReports));
      return true;
    }

    return false;
  }

  @Override
  @Transactional
  public void approveReview(Review review, String reason) {
    review.setModerationStatus(ModerationStatus.APPROVED);
    review.setModerationReason(reason);
    review.setModeratedAt(LocalDateTime.now());
    
    // Lấy admin hiện tại
    try {
      UserAccount admin = userAccountRepository.findById(SecurityContext.getCurrentUserId())
          .orElse(null);
      review.setModeratedBy(admin);
    } catch (Exception e) {
      // Nếu không có admin context (tự động), để null
      log.debug("No admin context for moderation, leaving moderatedBy as null");
    }

    reviewRepository.save(review);
    log.info("Review {} approved by admin", review.getId());
  }

  @Override
  @Transactional
  public void rejectReview(Review review, String reason) {
    if (reason == null || reason.isBlank()) {
      throw new IllegalArgumentException("Lý do từ chối không được để trống");
    }

    review.setModerationStatus(ModerationStatus.REJECTED);
    review.setModerationReason(reason);
    review.setModeratedAt(LocalDateTime.now());
    
    // Lấy admin hiện tại
    try {
      UserAccount admin = userAccountRepository.findById(SecurityContext.getCurrentUserId())
          .orElse(null);
      review.setModeratedBy(admin);
    } catch (Exception e) {
      // Nếu không có admin context (tự động), để null
      log.debug("No admin context for moderation, leaving moderatedBy as null");
    }

    reviewRepository.save(review);
    log.info("Review {} rejected by admin: {}", review.getId(), reason);
  }

  @Override
  @Transactional
  public void hideReview(Review review, String reason) {
    review.setModerationStatus(ModerationStatus.HIDDEN);
    if (reason != null && !reason.isBlank()) {
      review.setModerationReason(reason);
    }
    review.setModeratedAt(LocalDateTime.now());
    
    // Nếu có admin đang thực hiện, gán admin đó
    try {
      UserAccount admin = userAccountRepository.findById(SecurityContext.getCurrentUserId())
          .orElse(null);
      review.setModeratedBy(admin);
    } catch (Exception e) {
      // Nếu không có admin context (tự động), để null
      log.debug("No admin context for moderation, leaving moderatedBy as null");
    }

    reviewRepository.save(review);
    log.info("Review {} hidden: {}", review.getId(), reason);
  }
}

