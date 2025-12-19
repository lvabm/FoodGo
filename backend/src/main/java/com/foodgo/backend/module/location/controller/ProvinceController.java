package com.foodgo.backend.module.location.controller;

import com.foodgo.backend.module.location.dto.request.ProvinceFilterRequest;
import com.foodgo.backend.module.location.dto.response.ProvinceResponse;
import com.foodgo.backend.module.location.service.ProvinceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
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

  private final ProvinceService provinceService;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Tỉnh/Thành (Không phân trang)")
  public List<ProvinceResponse> getAll() {
    return provinceService.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Tỉnh/Thành theo ID")
  public ProvinceResponse getDetail(@PathVariable Integer id) {
    return provinceService.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Tỉnh/Thành")
  public Page<ProvinceResponse> search(
      @ModelAttribute ProvinceFilterRequest filter, Pageable pageable) {
    return provinceService.getPage(filter, pageable);
  }
}
