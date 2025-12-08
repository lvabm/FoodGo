package com.foodgo.backend.module.location.controller;

import com.foodgo.backend.module.location.dto.DistrictFilterRequest;
import com.foodgo.backend.module.location.dto.DistrictResponse;
import com.foodgo.backend.module.location.service.DistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "District Management (Read Only)", description = "Quản lý dữ liệu Quận/Huyện (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {

  private final DistrictService districtService;

  @GetMapping
  @Operation(summary = "Lấy tất cả Quận/Huyện (Không phân trang)")
  public List<DistrictResponse> getAll() {
    return districtService.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Quận/Huyện theo ID")
  public DistrictResponse getDetail(@PathVariable Integer id) {
    return districtService.getDetail(id);
  }

  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Quận/Huyện")
  public Page<DistrictResponse> search(
      @ModelAttribute DistrictFilterRequest filter, Pageable pageable) {
    return districtService.getPage(filter, pageable);
  }
}
