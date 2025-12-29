package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.service.AdminOutletService;
import com.foodgo.backend.module.notification.service.NotificationService;
import com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOutletServiceImpl
    extends BaseServiceImpl<
        Outlet,
        UUID,
        Object,
        Object,
        OutletFilterRequest,
        OutletResponse>
    implements AdminOutletService {

  private final OutletRepository outletRepository;
  private final OutletMapper outletMapper;
  private final NotificationService notificationService;

  private final String entityName = EntityName.OUTLET.getFriendlyName() + " (Admin)";

  @Override
  protected JpaRepository<Outlet, UUID> getRepository() {
    return outletRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Outlet> getSpecRepository() {
    return outletRepository;
  }

  @Override
  protected BaseMapper<Outlet, Object, Object, OutletResponse> getMapper() {
    // OutletMapper extends BaseMapper<Outlet, OutletCreateRequest, OutletUpdateRequest, OutletResponse>
    // We use Object, Object for admin service since we don't need create/update
    // Create a wrapper that adapts OutletMapper to our needs
    return new BaseMapper<Outlet, Object, Object, OutletResponse>() {
      @Override
      public OutletResponse toResponse(Outlet entity) {
        return outletMapper.toResponse(entity);
      }
      
      @Override
      public java.util.List<OutletResponse> toResponseList(java.util.List<Outlet> entities) {
        return outletMapper.toResponseList(entities);
      }
      
      @Override
      public Outlet toEntity(Object createRequest) {
        throw new UnsupportedOperationException("Admin service does not support create operations");
      }
      
      @Override
      public void updateEntity(Object updateRequest, Outlet entity) {
        throw new UnsupportedOperationException("Admin service does not support update operations");
      }
    };
  }

  @Override
  protected String getEntityName() {
    return entityName;
  }

  @Override
  protected void ensurePermission(Outlet entity) {
    if (!SecurityContext.isAdmin()) {
      throw new AccessDeniedException("Chỉ Admin mới có quyền thao tác này.");
    }
  }

  @Override
  protected Specification<Outlet> buildSpecification(OutletFilterRequest filter) {
    return new OutletSearchSpecification(filter);
  }

  @Override
  @Transactional
  public OutletResponse approveOutlet(UUID id) {
    Outlet outlet = findByIdOrThrow(id);
    
    if (outlet.isActive()) {
      throw new BadRequestException("Outlet này đã được duyệt và đang hoạt động.");
    }
    
    outlet.setActive(true);
    Outlet saved = outletRepository.save(outlet);
    
    // Gửi thông báo cho owner
    try {
      if (outlet.getOwner() != null) {
        notificationService.createNotificationAsync(
            outlet.getOwner().getId(),
            "OUTLET_APPROVED",
            "Outlet đã được duyệt",
            String.format("Outlet '%s' của bạn đã được Admin duyệt và đang hoạt động.", outlet.getName()),
            outlet.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for approved outlet {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã duyệt outlet thành công.");
    return outletMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public OutletResponse lockOutlet(UUID id, String reason) {
    Outlet outlet = findByIdOrThrow(id);
    
    if (!outlet.isActive()) {
      throw new BadRequestException("Outlet này đã bị khóa hoặc chưa được duyệt.");
    }
    
    outlet.setActive(false);
    if (outlet.getDescription() != null) {
      outlet.setDescription(outlet.getDescription() + " [Bị khóa bởi Admin. Lý do: " + reason + "]");
    } else {
      outlet.setDescription("[Bị khóa bởi Admin. Lý do: " + reason + "]");
    }
    
    Outlet saved = outletRepository.save(outlet);
    
    // Gửi thông báo cho owner
    try {
      if (outlet.getOwner() != null) {
        notificationService.createNotificationAsync(
            outlet.getOwner().getId(),
            "OUTLET_LOCKED",
            "Outlet đã bị khóa",
            String.format("Outlet '%s' của bạn đã bị Admin khóa. Lý do: %s", outlet.getName(), reason),
            outlet.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for locked outlet {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã khóa outlet thành công.");
    return outletMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public OutletResponse unlockOutlet(UUID id) {
    Outlet outlet = findByIdOrThrow(id);
    
    if (outlet.isActive()) {
      throw new BadRequestException("Outlet này đang hoạt động.");
    }
    
    outlet.setActive(true);
    Outlet saved = outletRepository.save(outlet);
    
    // Gửi thông báo cho owner
    try {
      if (outlet.getOwner() != null) {
        notificationService.createNotificationAsync(
            outlet.getOwner().getId(),
            "OUTLET_UNLOCKED",
            "Outlet đã được mở khóa",
            String.format("Outlet '%s' của bạn đã được Admin mở khóa và đang hoạt động trở lại.", outlet.getName()),
            outlet.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for unlocked outlet {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã mở khóa outlet thành công.");
    return outletMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public OutletResponse deleteOutlet(UUID id, String reason) {
    Outlet outlet = findByIdOrThrow(id);
    
    if (Boolean.TRUE.equals(outlet.getIsDeleted())) {
      throw new BadRequestException("Outlet này đã bị xóa trước đó.");
    }
    
    outlet.setIsDeleted(true);
    if (outlet.getDescription() != null) {
      outlet.setDescription(outlet.getDescription() + " [Đã xóa bởi Admin. Lý do: " + reason + "]");
    } else {
      outlet.setDescription("[Đã xóa bởi Admin. Lý do: " + reason + "]");
    }
    
    Outlet saved = outletRepository.save(outlet);
    
    // Gửi thông báo cho owner
    try {
      if (outlet.getOwner() != null) {
        notificationService.createNotificationAsync(
            outlet.getOwner().getId(),
            "OUTLET_DELETED",
            "Outlet đã bị xóa",
            String.format("Outlet '%s' của bạn đã bị Admin xóa. Lý do: %s", outlet.getName(), reason),
            outlet.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for deleted outlet {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã xóa outlet thành công.");
    return outletMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public OutletResponse restoreOutlet(UUID id) {
    Outlet outlet = findByIdOrThrow(id);
    
    if (!Boolean.TRUE.equals(outlet.getIsDeleted())) {
      throw new BadRequestException("Outlet này chưa bị xóa.");
    }
    
    outlet.setIsDeleted(false);
    outlet.setActive(true); // Tự động kích hoạt khi restore
    Outlet saved = outletRepository.save(outlet);
    
    // Gửi thông báo cho owner
    try {
      if (outlet.getOwner() != null) {
        notificationService.createNotificationAsync(
            outlet.getOwner().getId(),
            "OUTLET_RESTORED",
            "Outlet đã được khôi phục",
            String.format("Outlet '%s' của bạn đã được Admin khôi phục và đang hoạt động trở lại.", outlet.getName()),
            outlet.getId()
        );
      }
    } catch (Exception e) {
      log.warn("Failed to send notification for restored outlet {}: {}", id, e.getMessage());
    }
    
    SuccessMessageContext.setMessage("Đã khôi phục outlet thành công.");
    return outletMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public List<OutletResponse> bulkApprove(List<UUID> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new BadRequestException("Danh sách ID không được để trống.");
    }
    
    List<Outlet> outlets = outletRepository.findAllById(ids);
    
    if (outlets.size() != ids.size()) {
      throw new ResourceNotFoundException("Một số outlet không tồn tại.");
    }
    
    outlets.forEach(outlet -> {
      if (!outlet.isActive()) {
        outlet.setActive(true);
        
        // Gửi thông báo cho owner
        try {
          if (outlet.getOwner() != null) {
            notificationService.createNotificationAsync(
                outlet.getOwner().getId(),
                "OUTLET_APPROVED",
                "Outlet đã được duyệt",
                String.format("Outlet '%s' của bạn đã được Admin duyệt.", outlet.getName()),
                outlet.getId()
            );
          }
        } catch (Exception e) {
          log.warn("Failed to send notification for approved outlet {}: {}", outlet.getId(), e.getMessage());
        }
      }
    });
    
    List<Outlet> saved = outletRepository.saveAll(outlets);
    SuccessMessageContext.setMessage(String.format("Đã duyệt %d outlet thành công.", saved.size()));
    
    return saved.stream()
        .map(outletMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<OutletResponse> bulkLock(List<UUID> ids, String reason) {
    if (ids == null || ids.isEmpty()) {
      throw new BadRequestException("Danh sách ID không được để trống.");
    }
    
    List<Outlet> outlets = outletRepository.findAllById(ids);
    
    if (outlets.size() != ids.size()) {
      throw new ResourceNotFoundException("Một số outlet không tồn tại.");
    }
    
    outlets.forEach(outlet -> {
      if (outlet.isActive()) {
        outlet.setActive(false);
        if (outlet.getDescription() != null) {
          outlet.setDescription(outlet.getDescription() + " [Bị khóa bởi Admin. Lý do: " + reason + "]");
        } else {
          outlet.setDescription("[Bị khóa bởi Admin. Lý do: " + reason + "]");
        }
        
        // Gửi thông báo cho owner
        try {
          if (outlet.getOwner() != null) {
            notificationService.createNotificationAsync(
                outlet.getOwner().getId(),
                "OUTLET_LOCKED",
                "Outlet đã bị khóa",
                String.format("Outlet '%s' của bạn đã bị Admin khóa. Lý do: %s", outlet.getName(), reason),
                outlet.getId()
            );
          }
        } catch (Exception e) {
          log.warn("Failed to send notification for locked outlet {}: {}", outlet.getId(), e.getMessage());
        }
      }
    });
    
    List<Outlet> saved = outletRepository.saveAll(outlets);
    SuccessMessageContext.setMessage(String.format("Đã khóa %d outlet thành công.", saved.size()));
    
    return saved.stream()
        .map(outletMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<OutletResponse> bulkUnlock(List<UUID> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new BadRequestException("Danh sách ID không được để trống.");
    }
    
    List<Outlet> outlets = outletRepository.findAllById(ids);
    
    if (outlets.size() != ids.size()) {
      throw new ResourceNotFoundException("Một số outlet không tồn tại.");
    }
    
    outlets.forEach(outlet -> {
      if (!outlet.isActive()) {
        outlet.setActive(true);
        
        // Gửi thông báo cho owner
        try {
          if (outlet.getOwner() != null) {
            notificationService.createNotificationAsync(
                outlet.getOwner().getId(),
                "OUTLET_UNLOCKED",
                "Outlet đã được mở khóa",
                String.format("Outlet '%s' của bạn đã được Admin mở khóa.", outlet.getName()),
                outlet.getId()
            );
          }
        } catch (Exception e) {
          log.warn("Failed to send notification for unlocked outlet {}: {}", outlet.getId(), e.getMessage());
        }
      }
    });
    
    List<Outlet> saved = outletRepository.saveAll(outlets);
    SuccessMessageContext.setMessage(String.format("Đã mở khóa %d outlet thành công.", saved.size()));
    
    return saved.stream()
        .map(outletMapper::toResponse)
        .collect(Collectors.toList());
  }
}

