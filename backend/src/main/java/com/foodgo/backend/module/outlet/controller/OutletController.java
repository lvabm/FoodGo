package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.service.OutletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
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

  private final OutletService outletService;

  // --- 🔑 API Ghi Dữ Liệu (Yêu cầu Owner ID) ---

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Tạo mới Outlet",
      description = "Chỉ Owner mới có thể tạo Outlet và Owner ID được gán tự động.")
  public OutletResponse createOutlet(@Valid @RequestBody OutletCreateRequest request) {
    return outletService.create(request);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật Outlet", description = "Chỉ Owner sở hữu mới có thể cập nhật.")
  public OutletResponse updateOutlet(
      @PathVariable UUID id, @Valid @RequestBody OutletUpdateRequest request) {
    return outletService.update(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Xóa mềm (Soft Delete) Outlet",
      description = "Chỉ Owner sở hữu mới có thể xóa.")
  public void softDeleteOutlet(@PathVariable UUID id) {
    outletService.softDelete(id);
  }

  // --- API Đọc Dữ Liệu (API Ưu tiên số 1) ---

  @GetMapping("/my-outlet")
  @Operation(summary = "Lấy Outlet của chính mình (Owner)")
  public OutletResponse getMyOutlet() {
    return outletService.getOwnerOutlet();
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(
      summary = "Tìm kiếm và Phân trang Outlet (Hiệu suất cao)",
      description = "Hỗ trợ lọc theo tên, quận, loại, Price Range, và Đặc điểm (features).")
  public Page<OutletResponse> searchOutlets(
      @ModelAttribute OutletFilterRequest filter, Pageable pageable) {
    return outletService.getPage(filter, pageable);
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Outlet theo ID")
  public OutletResponse getDetail(@PathVariable UUID id) {
    return outletService.getDetail(id);
  }
}
