package com.foodgo.backend.module.review.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.review.dto.request.create.ReviewCreateRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewResponse;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.review.entity.ReviewImage;
import com.foodgo.backend.module.review.entity.ReviewReply;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import org.mapstruct.ReportingPolicy;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper
    extends BaseMapper<Review, ReviewCreateRequest, ReviewUpdateRequest, ReviewResponse> {

  @Override
  @Mapping(source = "booking.id", target = "bookingId")
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user.profile.fullName", target = "userName")
  @Mapping(source = "user.profile.avatarUrl", target = "userAvatar")
  @Mapping(source = "outlet.id", target = "outletId")
  @Mapping(source = "outlet.name", target = "outletName")
  @Mapping(target = "images", expression = "java(mapImages(entity.getReviewImages()))")
  @Mapping(source = "reviewReply", target = "reply")
  @Mapping(source = "createAt", target = "createdAt")
  @Mapping(source = "moderationStatus", target = "moderationStatus")
  @Mapping(source = "moderationReason", target = "moderationReason")
  @Mapping(source = "moderatedAt", target = "moderatedAt")
  @Mapping(target = "moderatedBy", expression = "java(mapModeratedBy(entity))")
  ReviewResponse toResponse(Review entity);
  
  // Helper: Map moderatedBy safely
  default UUID mapModeratedBy(Review entity) {
    return entity.getModeratedBy() != null ? entity.getModeratedBy().getId() : null;
  }

  // Custom mapping cho Reply
  default ReviewResponse.ReplyResponse mapReply(ReviewReply reply) {
    if (reply == null) {
      return null;
    }
    String ownerName = reply.getOwner() != null && reply.getOwner().getProfile() != null
        ? reply.getOwner().getProfile().getFullName()
        : null;
    // Note: ReviewReply extends BaseIntegerEntity which doesn't have createAt field
    // Using null for createdAt as it's not available in the entity
    return new ReviewResponse.ReplyResponse(
        ownerName,
        reply.getReplyText(),
        null // createdAt not available in ReviewReply entity
    );
  }

  // Helper: List<Entity> -> List<String>
  default List<String> mapImages(List<ReviewImage> images) {
    if (images == null) return List.of();
    return images.stream().map(ReviewImage::getImageUrl).collect(Collectors.toList());
  }
}
