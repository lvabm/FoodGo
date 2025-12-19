package com.foodgo.backend.module.location.controller;

import com.foodgo.backend.module.location.dto.request.CountryFilterRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import com.foodgo.backend.module.location.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Country Management (Read Only)", description = "Quản lý dữ liệu Quốc gia (Chỉ đọc)")
@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

  private final CountryService countryService;

  @PermitAll
  @GetMapping
  @Operation(summary = "Lấy tất cả Quốc gia (Không phân trang)")
  public List<CountryResponse> getAll() {
    return countryService.getAll();
  }

  @PermitAll
  @GetMapping("/{id}")
  @Operation(summary = "Lấy chi tiết Quốc gia theo ID")
  public CountryResponse getDetail(@PathVariable Integer id) {
    return countryService.getDetail(id);
  }

  @PermitAll
  @GetMapping("/search")
  @Operation(summary = "Tìm kiếm và phân trang Quốc gia")
  public Page<CountryResponse> search(
      @ModelAttribute CountryFilterRequest filter, Pageable pageable) {
    return countryService.getPage(filter, pageable);
  }
}
