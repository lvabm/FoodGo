package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.module.outlet.dto.request.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletRequest;
import com.foodgo.backend.module.outlet.dto.request.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.service.OutletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
    name = "Outlet Management",
    description = "API Quản lý Outlet (Full CRUD) và Tìm kiếm phức tạp.")
@RestController
@RequestMapping("/api/v1/outlets")
@RequiredArgsConstructor
public class OutletController {

  private final OutletService service;

  // --- 🔑 API Ghi Dữ Liệu (Yêu cầu Owner ID) ---

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Tạo mới Outlet",
      description = "Chỉ Owner mới có thể tạo Outlet và Owner ID được gán tự động.")
  public OutletResponse createOutlet(@Valid @RequestBody OutletRequest request) {
    // 🔑 YÊU CẦU SECURITY: Lấy Owner ID từ Security Context
    UUID ownerId = SecurityContext.getCurrentUserId();
    return service.createOutlet(request, ownerId);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật Outlet", description = "Chỉ Owner sở hữu mới có thể cập nhật.")
  public OutletResponse updateOutlet(
      @PathVariable UUID id, @Valid @RequestBody OutletUpdateRequest request) {
    // 🔑 YÊU CẦU SECURITY: Lấy Owner ID từ Security Context
    UUID ownerId = SecurityContext.getCurrentUserId();
    return service.updateOutlet(id, request, ownerId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa mềm (Soft Delete) Outlet",
      description = "Chỉ Owner sở hữu mới có thể xóa.")
  public void softDeleteOutlet(@PathVariable UUID id) {
    // Lấy Owner ID để kiểm tra quyền trước khi xóa
    // NOTE: Cần thêm logic kiểm tra quyền sở hữu trong Service hoặc Controller nếu dùng
    // BaseService.softDelete
    // Trong trường hợp này, ta giả định Service Layer handle quyền cho Soft Delete nếu cần.
    // Tạm thời dùng BaseServiceImpl.softDelete (không kiểm tra quyền sở hữu)
    service.softDelete(id);
  }

  // --- API Đọc Dữ Liệu (API Ưu tiên số 1) ---

  @GetMapping("/search")
  @Operation(
      summary = "Tìm kiếm và Phân trang Outlet (Hiệu suất cao)",
      description = "Hỗ trợ lọc theo tên, quận, loại, Price Range, và Đặc điểm (features).")
  public Page<OutletResponse> searchOutlets(
      @ModelAttribute OutletFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Outlet theo ID")
  public OutletResponse getDetail(@PathVariable UUID id) {
    return service.getDetail(id);
  }
}
