package com.foodgo.backend.module.location.controller;

import com.foodgo.backend.module.location.dto.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.ProvinceResponse;
import com.foodgo.backend.module.location.service.ProvinceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Province Management (Read Only)", description = "Quản lý dữ liệu Tỉnh/Thành (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {

  private final ProvinceService service;

  @GetMapping
  @Operation(summary = "Lấy tất cả Tỉnh/Thành (Không phân trang)")
  public List<ProvinceResponse> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Tỉnh/Thành theo ID")
  public ProvinceResponse getDetail(@PathVariable Integer id) {
    return service.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Tỉnh/Thành")
  public Page<ProvinceResponse> search(
      @ModelAttribute ProvinceFilterRequest filter, Pageable pageable) {
    return service.getPage(filter, pageable);
  }
}
