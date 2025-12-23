package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ForbiddenException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.outlet.dto.mapper.OutletImageMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletImageResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletImage;
import com.foodgo.backend.module.outlet.repository.OutletImageRepository;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.outlet.service.OutletImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutletImageServiceImpl
    extends BaseServiceImpl<OutletImage, Integer, Object, Object, Object, OutletImageResponse>
    implements OutletImageService {

  private final String outletImageEntityName = EntityName.OUTLET_IMAGE.getFriendlyName();

  private final OutletImageRepository outletImageRepository;
  private final OutletImageMapper outletImageMapper;
  private final OutletRepository outletRepository;

  @Override
  protected JpaRepository<OutletImage, Integer> getRepository() {
    return outletImageRepository;
  }

  @Override
  protected JpaSpecificationExecutor<OutletImage> getSpecRepository() {
    return outletImageRepository;
  }

  @Override
  protected BaseMapper<OutletImage, Object, Object, OutletImageResponse> getMapper() {
    return outletImageMapper;
  }

  @Override
  protected String getEntityName() {
    return outletImageEntityName;
  }

  @Override
  protected Specification<OutletImage> buildSpecification(Object filter) {
    return null;
  }

  @Override
  protected void ensurePermission(OutletImage entity) {
    if (SecurityContext.isAdmin()) return;

    UUID currentUserId = SecurityContext.getCurrentUserId();
    if (!entity.getOutlet().getOwner().getId().equals(currentUserId)) {
      throw new ForbiddenException("Bạn không có quyền thao tác với hình ảnh này.");
    }
  }

  @Override
  @Transactional
  public List<OutletImageResponse> uploadImages(UUID outletId, List<MultipartFile> files) {
    // 1. Validate Outlet
    Outlet outlet =
        outletRepository
            .findById(outletId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cửa hàng không tồn tại: " + outletId));

    // 2. Validate Permission
    if (!SecurityContext.isAdmin()) {
      UUID currentUserId = SecurityContext.getCurrentUserId();
      if (!outlet.getOwner().getId().equals(currentUserId)) {
        throw new ForbiddenException("Bạn không có quyền đăng ảnh cho cửa hàng này.");
      }
    }

    List<OutletImage> savedImages = new ArrayList<>();

    // Logic tính displayOrder: đếm số ảnh hiện có để cộng tiếp (đơn giản hóa cho MVP)
    // int currentCount = outlet.getImages().size(); // Nếu có mapping OneToMany

    for (int i = 0; i < files.size(); i++) {
      MultipartFile file = files.get(i);

      // 3. Fake Upload Logic
      String originalFilename = file.getOriginalFilename();
      String fakeUrl =
          "https://via.placeholder.com/600x400?text="
              + (originalFilename != null ? originalFilename.replace(" ", "+") : "Image");

      OutletImage image =
          OutletImage.builder()
              .outlet(outlet)
              .imageUrl(fakeUrl) // Dùng đúng field imageUrl
              .displayOrder(i + 1) // Gán thứ tự hiển thị
              .isPrimary(
                  i == 0 && savedImages.isEmpty()) // Set ảnh đầu tiên là Primary nếu muốn logic đó
              .build();

      savedImages.add(outletImageRepository.save(image));
    }

    SuccessMessageContext.setMessage("Đã tải lên " + savedImages.size() + " hình ảnh.");

    return savedImages.stream().map(outletImageMapper::toResponse).toList();
  }
}
