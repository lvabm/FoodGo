package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.request.create.OperatingHoursCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OperatingHoursFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OperatingHoursUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OperatingHoursResponse;
import com.foodgo.backend.module.outlet.service.OperatingHoursService;
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
    name = "Operating Hours",
    description = "API Quản lý giờ hoạt động chi tiết theo từng ngày của Outlet.")
@RestController
@RequestMapping("/api/v1/outlets/{outletId}/operating-hours")
@RequiredArgsConstructor
public class OperatingHoursController {

  private final OperatingHoursService service;

  // 1. CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Tạo mới giờ hoạt động",
      description = "Owner/Admin thêm giờ hoạt động (dayOfWeek, openTime, closeTime).")
  public OperatingHoursResponse createHours(
      @PathVariable UUID outletId, @Valid @RequestBody OperatingHoursCreateRequest request) {
    OperatingHoursCreateRequest finalRequest =
        new OperatingHoursCreateRequest(
            request.dayOfWeek(),
            request.openTime(),
            request.closeTime(),
            request.isClosed(),
            outletId);
    return service.create(finalRequest);
  }

  // 2. UPDATE
  @PatchMapping("/{id}")
  @Operation(
      summary = "Cập nhật giờ hoạt động (Partial Update)",
      description = "Owner/Admin cập nhật giờ hoạt động theo ID.")
  public OperatingHoursResponse updateHours(
      @PathVariable Integer id, @Valid @RequestBody OperatingHoursUpdateRequest request) {
    return service.update(id, request);
  }

  // 3. SOFT DELETE
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Xóa mềm (Soft Delete) giờ hoạt động",
      description = "Owner/Admin xóa mềm một giờ hoạt động.")
  public OperatingHoursResponse softDeleteHours(@PathVariable Integer id) {
    return service.softDelete(id);
  }

  // 4. GET DETAIL
  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết giờ hoạt động theo ID")
  public OperatingHoursResponse getDetailHours(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  // 5. GET SEARCH / List theo Outlet
  @PermitAll
  @GetMapping
  @Operation(
      summary = "Lấy danh sách giờ hoạt động của một Outlet",
      description = "Công khai (Public): Xem tất cả giờ hoạt động của Outlet này.")
  public Page<OperatingHoursResponse> searchHours(
      @PathVariable UUID outletId,
      @ModelAttribute OperatingHoursFilterRequest filter,
      Pageable pageable) {
    // Tùy chỉnh FilterRequest để buộc lọc theo Outlet ID từ PathVariable
    OperatingHoursFilterRequest finalFilter =
        new OperatingHoursFilterRequest(outletId, filter.dayOfWeek());
    return service.getPage(finalFilter, pageable);
  }
}
