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
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import jakarta.persistence.criteria.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    Review saved = reviewRepository.save(review);
    SuccessMessageContext.setMessage("Cảm ơn bạn đã đánh giá!");
    return reviewMapper.toResponse(saved);
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

    SuccessMessageContext.setMessage("Cập nhật đánh giá thành công.");
    return reviewMapper.toResponse(review);
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

    replyRepository.save(
        ReviewReply.builder()
            .review(review)
            .owner(userAccountRepository.getReferenceById(userId))
            .replyText(replyText)
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
      if (Long.class != query.getResultType()) {
        root.fetch("outlet", JoinType.LEFT);
        root.fetch("user", JoinType.LEFT).fetch("profile", JoinType.LEFT);
        root.fetch("booking", JoinType.LEFT);
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
}
