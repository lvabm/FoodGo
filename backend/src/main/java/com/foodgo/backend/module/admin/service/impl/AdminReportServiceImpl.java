package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.ReportStatus;
import com.foodgo.backend.module.admin.service.AdminReportService;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.review.dto.criteria.ReviewReportSpecification;
import com.foodgo.backend.common.constant.ReportStatus;
import com.foodgo.backend.module.review.dto.mapper.ReviewReportMapper;
import com.foodgo.backend.module.review.dto.request.filter.ReviewReportFilterRequest;
import com.foodgo.backend.module.review.dto.request.update.ReviewReportStatusUpdateRequest;
import com.foodgo.backend.module.review.dto.response.ReviewReportResponse;
import com.foodgo.backend.module.review.entity.ReviewReport;
import com.foodgo.backend.module.review.repository.ReviewReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReportServiceImpl
    extends BaseServiceImpl<
        ReviewReport,
        Integer,
        Object,
        Object,
        ReviewReportFilterRequest,
        ReviewReportResponse>
    implements AdminReportService {

  private final ReviewReportRepository reviewReportRepository;
  private final ReviewReportMapper reviewReportMapper;

  private final String entityName = EntityName.REVIEW_REPORT.getFriendlyName();

  @Override
  protected JpaRepository<ReviewReport, Integer> getRepository() {
    return reviewReportRepository;
  }

  @Override
  protected JpaSpecificationExecutor<ReviewReport> getSpecRepository() {
    return reviewReportRepository;
  }

  @Override
  protected BaseMapper<ReviewReport, Object, Object, ReviewReportResponse> getMapper() {
    // Wrapper để adapt ReviewReportMapper thành BaseMapper với Object, Object
    return new BaseMapper<ReviewReport, Object, Object, ReviewReportResponse>() {
      @Override
      public ReviewReportResponse toResponse(ReviewReport entity) {
        return reviewReportMapper.toResponse(entity);
      }

      @Override
      public java.util.List<ReviewReportResponse> toResponseList(
          java.util.List<ReviewReport> entities) {
        return entities.stream().map(reviewReportMapper::toResponse).toList();
      }

      @Override
      public ReviewReport toEntity(Object createRequest) {
        throw new UnsupportedOperationException(
            "AdminReportService chỉ hỗ trợ Read operations");
      }

      @Override
      public void updateEntity(Object updateRequest, ReviewReport entity) {
        throw new UnsupportedOperationException(
            "AdminReportService chỉ hỗ trợ Read operations");
      }
    };
  }

  @Override
  protected String getEntityName() {
    return entityName;
  }

  @Override
  protected Specification<ReviewReport> buildSpecification(ReviewReportFilterRequest filter) {
    return new ReviewReportSpecification(filter);
  }

  @Override
  @Transactional
  public ReviewReportResponse updateStatus(Integer id, ReviewReportStatusUpdateRequest request) {
    ReviewReport report = reviewReportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            getEntityName() + " không tìm thấy với ID: " + id));
    
    report.setStatus(request.getStatus());
    ReviewReport updated = reviewReportRepository.save(report);
    
    SuccessMessageContext.setMessage("Đã cập nhật trạng thái báo cáo thành công.");
    return reviewReportMapper.toResponse(updated);
  }
}

