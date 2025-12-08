package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.OutletTypeFilterRequest;
import com.foodgo.backend.module.outlet.dto.OutletTypeResponse;
import com.foodgo.backend.module.outlet.service.OutletTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/outlet-types")
@RequiredArgsConstructor
@Tag(
    name = "Loại Cơ Sở (Outlet Types)",
    description = "APIs quản lý các loại hình cơ sở kinh doanh")
public class OutletTypeController {

  private final OutletTypeService service;

  @GetMapping
  @Operation(summary = "Lấy tất cả các loại cơ sở (Outlet Types)")
  public List<OutletTypeResponse> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy tất cả các loại cơ sở (Outlet Types)")
  public OutletTypeResponse getDetail(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm + phân trang Outlet Types")
  public Page<OutletTypeResponse> search(
      @ModelAttribute OutletTypeFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }
}
