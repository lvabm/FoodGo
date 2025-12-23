package com.foodgo.backend.module.outlet.controller;

import com.foodgo.backend.module.outlet.dto.response.OutletImageResponse;
import com.foodgo.backend.module.outlet.service.OutletImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outlet-images")
public class OutletImageController {

  private final OutletImageService outletImageService;

  @Operation(summary = "Upload danh sách ảnh cho cửa hàng (Owner/Admin)")
  @PostMapping(value = "/outlet/{outletId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<OutletImageResponse> uploadImages(
      @PathVariable UUID outletId, @RequestPart("files") List<MultipartFile> files) {
    return outletImageService.uploadImages(outletId, files);
  }

  @Operation(summary = "Xóa một ảnh (Owner/Admin)")
  @DeleteMapping("/{id}")
  public void hardDeleteImage(@PathVariable Integer id) {
    outletImageService.hardDelete(id);
  }

  @Operation(summary = "Lấy chi tiết một ảnh")
  @GetMapping("/{id}")
  public OutletImageResponse getById(@PathVariable Integer id) {
    return outletImageService.getDetail(id);
  }
}
