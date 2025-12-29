package com.foodgo.backend.module.review.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.BookingStatus;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.ReactionType;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.booking.repository.BookingRepository;
import com.foodgo.backend.module.review.dto.criteria.ReviewSpecification;
import com.foodgo.backend.module.review.dto.mapper.ReviewMapper;
import com.foodgo.backend.module.review.dto.request.create.ReviewCreateRequest;
import com.foodgo.backend.module.review.dto.request.filter.ReviewFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import com.foodgo.backend.module.review.entity.*;
import com.foodgo.backend.module.review.repository.*;
import com.foodgo.backend.module.review.service.ReviewService;
import com.foodgo.backend.module.moderation.service.ModerationService;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl
    extends BaseServiceImpl<
        Review, UUID, ReviewCreateRequest, ReviewUpdateRequest, ReviewFilterRequest, ReviewResponse>
    implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final BookingRepository bookingRepository;
  private final UserAccountRepository userAccountRepository;
  private final ReviewReplyRepository replyRepository;
  private final ReviewReactionRepository reactionRepository;
  private final ReviewImageRepository imageRepository;
  private final OutletRepository outletRepository;
  private final ModerationService moderationService;

  private final String reviewEntityName = EntityName.REVIEW.getFriendlyName();

  @Override
  protected JpaRepository<Review, UUID> getRepository() {
    return reviewRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Review> getSpecRepository() {
    return reviewRepository;
  }

  @Override
  protected BaseMapper<Review, ReviewCreateRequest, ReviewUpdateRequest, ReviewResponse>
      getMapper() {
    return reviewMapper;
  }

  @Override
  protected String getEntityName() {
    return reviewEntityName;
  }

  @Override
  protected Specification<Review> buildSpecification(ReviewFilterRequest filter) {
    return new ReviewSpecification(filter);
  }

  @Override
  protected void ensurePermission(Review entity) {
    if (SecurityContext.isAdmin()) return;
    if (!entity.getUser().getId().equals(SecurityContext.getCurrentUserId())) {
      throw new ForbiddenException("Bạn không có quyền chỉnh sửa đánh giá này.");
    }
  }

  // --- CREATE LOGIC ---
  @Override
  protected void validateBeforeCreate(ReviewCreateRequest request) {
    // Defensive validation: all four rating aspects must be present
    if (request.foodRating() == null
        || request.serviceRating() == null
        || request.ambianceRating() == null
        || request.priceRating() == null) {
      throw new BadRequestException("Vui lòng đánh giá tất cả tiêu chí: đồ ăn, phục vụ, không gian và giá cả.");
    }

    Booking booking =
        bookingRepository
            .findById(request.bookingId())
            .orElseThrow(() -> new BadRequestException("Đơn đặt bàn không tồn tại."));

    // 1. Check quyền: Phải là User của đơn
    if (!booking.getUser().getId().equals(SecurityContext.getCurrentUserId())) {
      throw new ForbiddenException("Bạn không phải là người đặt đơn này.");
    }
    // 2. Check trạng thái: Phải Completed
    if (booking.getStatus() != BookingStatus.COMPLETED) {
      throw new BadRequestException("Bạn chỉ được đánh giá sau khi hoàn tất dùng bữa.");
    }
    // 3. Check duplicate: 1 Booking chỉ 1 Review
    if (reviewRepository.existsByBookingId(booking.getId())) {
      throw new BadRequestException("Bạn đã đánh giá đơn hàng này rồi.");
    }
    // 4. Check thời gian: chỉ được review trong vòng 30 ngày sau khi completed
    if (booking.getUserCheckedInAt() != null) {
      java.time.Instant completedTime = booking.getUserCheckedInAt();
      java.time.Instant now = java.time.Instant.now();
      long daysSinceCompleted = java.time.Duration.between(completedTime, now).toDays();
      
      if (daysSinceCompleted > com.foodgo.backend.common.constant.AppConstants.MAX_REVIEW_DAYS_AFTER_COMPLETED) {
        throw new BadRequestException(
            String.format("Bạn chỉ có thể đánh giá trong vòng %d ngày sau khi hoàn tất đơn hàng.", 
                com.foodgo.backend.common.constant.AppConstants.MAX_REVIEW_DAYS_AFTER_COMPLETED));
      }
    }
  }

  @Override
  @Transactional
  public ReviewResponse create(ReviewCreateRequest request) {
    validateBeforeCreate(request);

    Booking booking = bookingRepository.getReferenceById(request.bookingId());

    Review review = reviewMapper.toEntity(request);
    review.setBooking(booking);
    review.setUser(userAccountRepository.getReferenceById(SecurityContext.getCurrentUserId()));
    review.setOutlet(booking.getOutlet()); // Lấy Outlet từ Booking

    // Tính điểm Overall trung bình
    int avg =
        (request.foodRating()
                + request.serviceRating()
                + request.ambianceRating()
                + request.priceRating())
            / 4;
    review.setOverallRating(avg);

    // Xử lý ảnh
    if (request.imageUrls() != null && !request.imageUrls().isEmpty()) {
      List<ReviewImage> images =
          request.imageUrls().stream()
              .map(url -> ReviewImage.builder().review(review).imageUrl(url).build())
              .toList();
      review.setReviewImages(images);
    }

    // Lưu review trước
    Review savedReview = reviewRepository.save(review);

    // Tự động kiểm duyệt
    moderationService.autoModerateReview(savedReview);

    // Reload để lấy moderation status đã cập nhật
    savedReview = reviewRepository.findById(savedReview.getId()).orElse(savedReview);

    // Auto-update outlet rating và total reviews
    updateOutletRating(booking.getOutlet());
    
    SuccessMessageContext.setMessage("Cảm ơn bạn đã đánh giá!");
    return reviewMapper.toResponse(savedReview);
  }

  @Override
  @Transactional
  public ReviewResponse update(UUID id, ReviewUpdateRequest request) {
    Review review = findByIdOrThrow(id);
    ensurePermission(review);

    if (review.getCreateAt().isBefore(LocalDateTime.now().minusHours(24))) {
      throw new BadRequestException("Đã quá 24h, bạn không thể chỉnh sửa đánh giá này.");
    }

    reviewMapper.updateEntity(request, review);

    if (request.deletedImageIds() != null && !request.deletedImageIds().isEmpty()) {
      imageRepository.deleteAllByIdInAndReviewId(request.deletedImageIds(), id);
    }

    if (request.newImageUrls() != null && !request.newImageUrls().isEmpty()) {
      imageRepository.saveAll(
          request.newImageUrls().stream()
              .map(url -> ReviewImage.builder().review(review).imageUrl(url).build())
              .toList());
    }

    if (request.foodRating() != null
        || request.serviceRating() != null
        || request.ambianceRating() != null
        || request.priceRating() != null) {

      review.setOverallRating(
          (review.getFoodRating()
                  + review.getServiceRating()
                  + review.getAmbianceRating()
                  + review.getPriceRating())
              / 4);
    }
    
    Review updated = reviewRepository.save(review);
    
    // Auto-update outlet rating sau khi update review
    if (updated.getOutlet() != null) {
      updateOutletRating(updated.getOutlet());
    }

    SuccessMessageContext.setMessage("Cập nhật đánh giá thành công.");
    return reviewMapper.toResponse(updated);
  }

  // --- UPDATE LOGIC (24h Policy) ---
  @Override
  protected void validateBeforeUpdate(UUID id, ReviewUpdateRequest request) {
    Review review = findByIdOrThrow(id);
    ensurePermission(review);

    // Rule: Chỉ được sửa trong 24h
    if (review.getCreateAt().isBefore(LocalDateTime.now().minusHours(24))) {
      throw new BadRequestException("Đã quá 24h, bạn không thể chỉnh sửa đánh giá này nữa.");
    }
  }

  // --- REPLY LOGIC (Owner Only) ---
  @Override
  @Transactional
  public void replyToReview(UUID reviewId, String replyText) {
    Review review = findByIdOrThrow(reviewId);
    UUID userId = SecurityContext.getCurrentUserId();

    if (!review.getOutlet().getOwner().getId().equals(userId)) {
      throw new ForbiddenException("Chỉ chủ quán mới được phản hồi đánh giá này.");
    }

    if (review.getReviewReply() != null) {
      throw new BadRequestException("Đánh giá này đã được phản hồi.");
    }

    // Validate reply text
    if (replyText == null || replyText.trim().isEmpty()) {
      throw new BadRequestException("Nội dung phản hồi không được để trống.");
    }

    String trimmedText = replyText.trim();
    if (trimmedText.length() < 5) {
      throw new BadRequestException("Phản hồi phải có ít nhất 5 ký tự.");
    }

    if (trimmedText.length() > 1000) {
      throw new BadRequestException("Phản hồi không được vượt quá 1000 ký tự.");
    }

    replyRepository.save(
        ReviewReply.builder()
            .review(review)
            .owner(userAccountRepository.getReferenceById(userId))
            .replyText(trimmedText)
            .build());

    SuccessMessageContext.setMessage("Đã gửi phản hồi thành công.");
  }

  // --- REACTION LOGIC (Public/Auth) ---
  @Override
  @Transactional
  public void reactToReview(UUID reviewId, ReactionType type) {
    Review review = findByIdOrThrow(reviewId);
    UUID userId = SecurityContext.getCurrentUserId();

    Optional<ReviewReaction> optional =
        reactionRepository.findByReviewIdAndUserId(reviewId, userId);

    if (optional.isEmpty()) {
      // Chưa react → tạo mới
      ReviewReaction reaction =
          ReviewReaction.builder()
              .review(review)
              .user(userAccountRepository.getReferenceById(userId))
              .reactionType(type)
              .build();

      reactionRepository.save(reaction);
      updateReviewCount(review, type, 1);
      return;
    }

    ReviewReaction reaction = optional.get();
    ReactionType oldType = reaction.getReactionType();

    if (oldType == type) {
      // Bấm lại cùng nút → huỷ
      reactionRepository.delete(reaction);
      updateReviewCount(review, type, -1);
      return;
    }

    // Đổi LIKE ↔ DISLIKE
    reaction.setReactionType(type);
    reactionRepository.save(reaction);

    updateReviewCount(review, oldType, -1);
    updateReviewCount(review, type, 1);
  }

  private void updateReviewCount(Review review, ReactionType type, int delta) {
    if (type == ReactionType.LIKE) {
      review.setLikesCount(Math.max(0, review.getLikesCount() + delta));
    } else {
      review.setDislikesCount(Math.max(0, review.getDislikesCount() + delta));
    }
  }

  // --- RESTORE LOGIC (Admin Only) ---
  @Override
  @Transactional
  public ReviewResponse restore(UUID id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new com.foodgo.backend.common.exception.ResourceNotFoundException(
            getEntityName() + " không tìm thấy với ID: " + id));
    
    // Only admin can restore
    if (!SecurityContext.isAdmin()) {
      throw new ForbiddenException("Chỉ Admin mới có quyền khôi phục đánh giá.");
    }

    review.setIsDeleted(false);
    Review restored = reviewRepository.save(review);

    SuccessMessageContext.setMessage("Đã khôi phục đánh giá thành công.");
    return reviewMapper.toResponse(restored);
  }

  // Override getDetail to fetch join relationships
  @Override
  @Transactional(readOnly = true)
  public ReviewResponse getDetail(UUID id) {
    Specification<Review> specById = (root, query, cb) -> {
      // Fetch join để tránh LazyInitializationException
      if (query != null && Long.class != query.getResultType()) {
        root.fetch("outlet", JoinType.LEFT);
        root.fetch("user", JoinType.LEFT).fetch("profile", JoinType.LEFT);
        root.fetch("booking", JoinType.LEFT);
        // Fetch reviewReply và owner.profile để hiển thị phản hồi của chủ quán
        Fetch<Review, ReviewReply> replyFetch = root.fetch("reviewReply", JoinType.LEFT);
        replyFetch.fetch("owner", JoinType.LEFT).fetch("profile", JoinType.LEFT);
        query.distinct(true);
      }
      return cb.equal(root.get("id"), id);
    };
    
    // Don't filter by isDeleted for detail view (admin may want to see hidden reviews)
    Review review = reviewRepository.findAll(specById).stream()
        .findFirst()
        .orElseThrow(() -> new com.foodgo.backend.common.exception.ResourceNotFoundException(
            getEntityName() + " không tìm thấy với ID: " + id));

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));

    return reviewMapper.toResponse(review);
  }

  /**
   * Tự động cập nhật average rating và total reviews của outlet
   */
  private void updateOutletRating(Outlet outlet) {
    if (outlet == null || outlet.getId() == null) {
      return;
    }
    
    try {
      List<com.foodgo.backend.module.review.entity.Review> reviews = 
          reviewRepository.findByOutletIdIn(List.of(outlet.getId()));
      
      if (reviews == null || reviews.isEmpty()) {
        outlet.setAverageRating(null);
        outlet.setTotalReviews(0);
      } else {
        double avg = reviews.stream()
            .mapToInt(r -> r.getOverallRating() != null ? r.getOverallRating() : 0)
            .average()
            .orElse(0.0);
        
        if (avg > 0) {
          outlet.setAverageRating(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP));
        } else {
          outlet.setAverageRating(null);
        }
        
        outlet.setTotalReviews(reviews.size());
      }
      
      outletRepository.save(outlet);
    } catch (Exception e) {
      // Log error nhưng không fail review operation
      org.slf4j.LoggerFactory.getLogger(ReviewServiceImpl.class)
          .warn("Failed to update outlet rating for outlet {}: {}", outlet.getId(), e.getMessage());
    }
  }
}
