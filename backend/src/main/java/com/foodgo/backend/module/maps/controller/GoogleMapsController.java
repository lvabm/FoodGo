package com.foodgo.backend.module.maps.controller;

import com.foodgo.backend.common.base.dto.BaseResponse;
import com.foodgo.backend.module.maps.dto.request.DistanceRequest;
import com.foodgo.backend.module.maps.dto.request.GeocodeRequest;
import com.foodgo.backend.module.maps.dto.request.ReverseGeocodeRequest;
import com.foodgo.backend.module.maps.dto.response.DistanceResultResponse;
import com.foodgo.backend.module.maps.dto.response.GeocodeResultResponse;
import com.foodgo.backend.module.maps.service.GoogleMapsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
@Tag(name = "Google Maps", description = "API tích hợp Google Maps")
public class GoogleMapsController {

  private final GoogleMapsService googleMapsService;

  @PostMapping("/geocode")
  @Operation(summary = "Chuyển đổi địa chỉ thành tọa độ (Geocoding)")
  public ResponseEntity<BaseResponse<GeocodeResultResponse>> geocode(
      @Valid @RequestBody GeocodeRequest request) {
    GeocodeResultResponse result = googleMapsService.geocode(request);
    return ResponseEntity.ok(
        BaseResponse.<GeocodeResultResponse>builder()
            .success("OK".equals(result.getStatus()))
            .message(result.getMessage())
            .data(result)
            .build()
    );
  }

  @PostMapping("/reverse-geocode")
  @Operation(summary = "Chuyển đổi tọa độ thành địa chỉ (Reverse Geocoding)")
  public ResponseEntity<BaseResponse<GeocodeResultResponse>> reverseGeocode(
      @Valid @RequestBody ReverseGeocodeRequest request) {
    GeocodeResultResponse result = googleMapsService.reverseGeocode(request);
    return ResponseEntity.ok(
        BaseResponse.<GeocodeResultResponse>builder()
            .success("OK".equals(result.getStatus()))
            .message(result.getMessage())
            .data(result)
            .build()
    );
  }

  @PostMapping("/distance")
  @Operation(summary = "Tính khoảng cách và thời gian di chuyển giữa hai điểm")
  public ResponseEntity<BaseResponse<DistanceResultResponse>> calculateDistance(
      @Valid @RequestBody DistanceRequest request) {
    DistanceResultResponse result = googleMapsService.calculateDistance(request);
    return ResponseEntity.ok(
        BaseResponse.<DistanceResultResponse>builder()
            .success("OK".equals(result.getStatus()))
            .message(result.getMessage())
            .data(result)
            .build()
    );
  }

  @GetMapping("/geocode")
  @Operation(summary = "Chuyển đổi địa chỉ thành tọa độ (GET method)")
  public ResponseEntity<BaseResponse<GeocodeResultResponse>> geocodeGet(
      @RequestParam String address,
      @RequestParam(required = false, defaultValue = "vi") String language,
      @RequestParam(required = false, defaultValue = "VN") String region) {
    GeocodeRequest request = new GeocodeRequest();
    request.setAddress(address);
    request.setLanguage(language);
    request.setRegion(region);
    
    GeocodeResultResponse result = googleMapsService.geocode(request);
    return ResponseEntity.ok(
        BaseResponse.<GeocodeResultResponse>builder()
            .success("OK".equals(result.getStatus()))
            .message(result.getMessage())
            .data(result)
            .build()
    );
  }
}


