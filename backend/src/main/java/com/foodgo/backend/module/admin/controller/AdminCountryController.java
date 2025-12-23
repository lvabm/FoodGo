package com.foodgo.backend.module.admin.controller;

import com.foodgo.backend.module.admin.service.AdminCountryService;
import com.foodgo.backend.module.location.dto.request.create.CountryCreateRequest;
import com.foodgo.backend.module.location.dto.request.update.CountryUpdateRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin | Country Management", description = "API Quản lý CRUD Quốc gia - Chỉ Admin.")
@RestController
@RequestMapping("/api/v1/admin/countries")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCountryController {

  private final AdminCountryService adminCountryService;

  // 1. CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Tạo mới quốc gia")
  public CountryResponse createCountry(@Valid @RequestBody CountryCreateRequest request) {
    return adminCountryService.create(request);
  }

  // 2. UPDATE
  @PatchMapping("/{id}")
  @Operation(summary = "Cập nhật quốc gia (Partial Update)")
  public CountryResponse updateCountry(
      @PathVariable Integer id, @Valid @RequestBody CountryUpdateRequest request) {
    return adminCountryService.update(id, request);
  }

  // 3. SOFT DELETE
  @DeleteMapping("/{id}")
  @Operation(summary = "Xóa mềm (Soft Delete) quốc gia")
  public CountryResponse softDeleteCountry(@PathVariable Integer id) {
    return adminCountryService.softDelete(id);
  }

  // 4. HARD DELETE
  @DeleteMapping("/hard-delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Xóa cứng (Hard Delete) quốc gia")
  public void hardDeleteCountry(@PathVariable Integer id) {
    adminCountryService.hardDelete(id);
  }
}

