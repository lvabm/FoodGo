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
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
  ReviewResponse toResponse(Review entity);

  // Custom mapping cho Reply
  @Mapping(source = "owner.profile.fullName", target = "ownerName")
  ReviewResponse.ReplyResponse mapReply(ReviewReply reply);

  // Helper: List<Entity> -> List<String>
  default List<String> mapImages(List<ReviewImage> images) {
    if (images == null) return List.of();
    return images.stream().map(ReviewImage::getImageUrl).collect(Collectors.toList());
  }
}
