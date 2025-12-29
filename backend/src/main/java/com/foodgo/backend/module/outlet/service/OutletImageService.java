package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.DeletableService;
import com.foodgo.backend.common.base.service.ReadableService;
import com.foodgo.backend.module.outlet.dto.response.OutletImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface OutletImageService
    extends ReadableService<Integer, Object, OutletImageResponse>,
        DeletableService<Integer, OutletImageResponse> {

  // Logic đặc thù: Upload danh sách ảnh cho 1 Outlet
  List<OutletImageResponse> uploadImages(UUID outletId, List<MultipartFile> files);
}
