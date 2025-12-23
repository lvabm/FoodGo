package com.foodgo.backend.module.review.dto.mapper;

import com.foodgo.backend.module.review.dto.response.ReviewReportResponse;
import com.foodgo.backend.module.review.entity.ReviewReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewReportMapper {
  @Mapping(source = "review.id", target = "reviewId")
  @Mapping(target = "reviewSummary", expression = "java(getReviewSummary(entity))")
  @Mapping(source = "reporter.profile.fullName", target = "reporterName")
  @Mapping(source = "createdAt", target = "createdAt")
  ReviewReportResponse toResponse(ReviewReport entity);

  default String getReviewSummary(ReviewReport entity) {
    if (entity == null || entity.getReview() == null || entity.getReview().getComment() == null) {
      return null;
    }
    String comment = entity.getReview().getComment();
    return comment.length() > 100 ? comment.substring(0, 100) + "..." : comment;
  }
}

