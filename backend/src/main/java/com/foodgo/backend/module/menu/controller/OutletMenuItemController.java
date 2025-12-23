package com.foodgo.backend.module.menu.controller;

import com.foodgo.backend.module.menu.dto.request.OutletMenuItemCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletMenuItemFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletMenuItemUpdateRequest;
import com.foodgo.backend.module.menu.dto.response.OutletMenuItemResponse;
import com.foodgo.backend.module.menu.service.OutletMenuItemService;
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
    name = "Outlet Menu Item Management",
    description = "API Quản lý các món ăn tùy chỉnh trong Menu của Outlet.")
@RestController
@RequestMapping("/api/v1/outlets/{outletId}/menu-items")
@RequiredArgsConstructor
public class OutletMenuItemController {

  private final OutletMenuItemService outletMenuItemService;

  // --- API CỐT LÕI (CREATE) ---

  // 1. CREATE (API Ưu tiên 2)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Tạo mới món ăn tùy chỉnh cho Menu của Outlet",
      description = "Owner/Admin có thể thêm một món ăn mới vào Menu của Outlet đã sở hữu.")
  public OutletMenuItemResponse createMenuItem(
      @PathVariable UUID outletId, @Valid @RequestBody OutletMenuItemCreateRequest request) {
    OutletMenuItemCreateRequest finalRequest =
        new OutletMenuItemCreateRequest(
            request.name(),
            request.description(),
            request.price(),
            request.imageUrl(),
            outletId,
            request.menuItemId());
    return outletMenuItemService.create(finalRequest);
  }

  // --- CRUD KHÁC (Chỉ dùng itemId) ---

  // 2. UPDATE (Partial Update)
  @PatchMapping("/{itemId}")
  @Operation(
      summary = "Cập nhật món ăn tùy chỉnh (Partial Update)",
      description = "Chỉ Owner/Admin có thể cập nhật món ăn.")
  public OutletMenuItemResponse updateMenuItem(
      @PathVariable Integer itemId, @Valid @RequestBody OutletMenuItemUpdateRequest request) {
    return outletMenuItemService.update(itemId, request);
  }

  // 3. AVAILABILITY
  @PatchMapping("/{itemId}/availability")
  @Operation(
      summary = "Bật/Tắt trạng thái Available của món ăn",
      description = "Owner/Admin có thể nhanh chóng kích hoạt hoặc ngừng bán một món ăn.")
  public OutletMenuItemResponse toggleAvailability(@PathVariable Integer itemId) {
    return outletMenuItemService.toggleAvailability(itemId);
  }

  // 4. SOFT DELETE
  @DeleteMapping("/{itemId}")
  @Operation(
      summary = "Xóa mềm (Soft Delete) món ăn khỏi Menu",
      description = "Chỉ Owner/Admin có thể xóa mềm.")
  public OutletMenuItemResponse softDeleteMenuItem(@PathVariable Integer itemId) {
    return outletMenuItemService.softDelete(itemId);
  }

  // 5. GET DETAIL
  @PermitAll
  @GetMapping("/{itemId}")
  @Operation(summary = "Lấy chi tiết món ăn tùy chỉnh theo ID")
  public OutletMenuItemResponse getMenuItemDetail(@PathVariable Integer itemId) {
    return outletMenuItemService.getDetail(itemId);
  }

  // 6. GET SEARCH (Filter theo Outlet ID)
  @PermitAll
  @GetMapping("/search")
  @Operation(
      summary = "Tìm kiếm và Phân trang món ăn theo Outlet",
      description = "Tự động lọc theo Outlet ID từ Path Variable.")
  public Page<OutletMenuItemResponse> searchMenuItems(
      @PathVariable UUID outletId,
      @ModelAttribute OutletMenuItemFilterRequest filter,
      Pageable pageable) {
    OutletMenuItemFilterRequest finalFilter =
        new OutletMenuItemFilterRequest(filter.name(), outletId, filter.isAvailable());
    return outletMenuItemService.getPage(finalFilter, pageable);
  }

  // 7. LIST (compatibility) - support GET /outlets/{outletId}/menu-items?page=..&size=..
  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy danh sách món ăn của Outlet (dưới dạng phân trang)")
  public Page<OutletMenuItemResponse> listMenuItems(
      @PathVariable UUID outletId,
      @ModelAttribute OutletMenuItemFilterRequest filter,
      Pageable pageable) {
    // Reuse the same search logic but allow direct GET on the base path for compatibility with older clients
    OutletMenuItemFilterRequest finalFilter =
        new OutletMenuItemFilterRequest(filter.name(), outletId, filter.isAvailable());
    return outletMenuItemService.getPage(finalFilter, pageable);
  }
}
