package com.foodgo.backend.module.maps.service.impl;

import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.module.maps.config.GoogleMapsConfig;
import com.foodgo.backend.module.maps.dto.DistanceMatrixResponse;
import com.foodgo.backend.module.maps.dto.GeocodeResponse;
import com.foodgo.backend.module.maps.dto.request.DistanceRequest;
import com.foodgo.backend.module.maps.dto.request.GeocodeRequest;
import com.foodgo.backend.module.maps.dto.request.ReverseGeocodeRequest;
import com.foodgo.backend.module.maps.dto.response.DistanceResultResponse;
import com.foodgo.backend.module.maps.dto.response.GeocodeResultResponse;
import com.foodgo.backend.module.maps.service.GoogleMapsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleMapsServiceImpl implements GoogleMapsService {

  private final GoogleMapsConfig googleMapsConfig;
  private final RestTemplate restTemplate;

  @Override
  public GeocodeResultResponse geocode(GeocodeRequest request) {
    if (!googleMapsConfig.isEnabled() || googleMapsConfig.getApiKey() == null || googleMapsConfig.getApiKey().isEmpty()) {
      log.warn("Google Maps API is disabled or API key is not configured");
      return GeocodeResultResponse.builder()
          .status("DISABLED")
          .message("Google Maps API chưa được kích hoạt hoặc chưa cấu hình API key")
          .build();
    }

    try {
      String url = UriComponentsBuilder
          .fromUriString(googleMapsConfig.getBaseUrl() + "/geocode/json")
          .queryParam("address", URLEncoder.encode(request.getAddress(), StandardCharsets.UTF_8))
          .queryParam("key", googleMapsConfig.getApiKey())
          .queryParam("language", request.getLanguage())
          .queryParam("region", request.getRegion())
          .toUriString();

      log.debug("Geocoding request: {}", request.getAddress());

      ResponseEntity<GeocodeResponse> response = restTemplate.getForEntity(url, GeocodeResponse.class);
      GeocodeResponse geocodeResponse = response.getBody();

      if (geocodeResponse == null) {
        throw new BadRequestException("Không thể lấy dữ liệu từ Google Maps API");
      }

      if (!"OK".equals(geocodeResponse.getStatus())) {
        String errorMsg = geocodeResponse.getErrorMessage() != null 
            ? geocodeResponse.getErrorMessage() 
            : "Lỗi từ Google Maps API: " + geocodeResponse.getStatus();
        log.error("Geocoding failed: {}", errorMsg);
        return GeocodeResultResponse.builder()
            .status(geocodeResponse.getStatus())
            .message(errorMsg)
            .build();
      }

      if (geocodeResponse.getResults() == null || geocodeResponse.getResults().isEmpty()) {
        return GeocodeResultResponse.builder()
            .status("ZERO_RESULTS")
            .message("Không tìm thấy địa chỉ")
            .build();
      }

      GeocodeResponse.Result firstResult = geocodeResponse.getResults().get(0);
      GeocodeResponse.Result.Geometry.Location location = firstResult.getGeometry().getLocation();

      return GeocodeResultResponse.builder()
          .formattedAddress(firstResult.getFormattedAddress())
          .latitude(location.getLat())
          .longitude(location.getLng())
          .placeId(firstResult.getPlaceId())
          .status("OK")
          .message("Thành công")
          .build();

    } catch (Exception e) {
      log.error("Error during geocoding: {}", e.getMessage(), e);
      throw new BadRequestException("Lỗi khi lấy tọa độ từ địa chỉ: " + e.getMessage());
    }
  }

  @Override
  public GeocodeResultResponse reverseGeocode(ReverseGeocodeRequest request) {
    if (!googleMapsConfig.isEnabled() || googleMapsConfig.getApiKey() == null || googleMapsConfig.getApiKey().isEmpty()) {
      log.warn("Google Maps API is disabled or API key is not configured");
      return GeocodeResultResponse.builder()
          .status("DISABLED")
          .message("Google Maps API chưa được kích hoạt hoặc chưa cấu hình API key")
          .build();
    }

    try {
      String latlng = request.getLatitude() + "," + request.getLongitude();
      String url = UriComponentsBuilder
          .fromUriString(googleMapsConfig.getBaseUrl() + "/geocode/json")
          .queryParam("latlng", latlng)
          .queryParam("key", googleMapsConfig.getApiKey())
          .queryParam("language", request.getLanguage())
          .toUriString();

      log.debug("Reverse geocoding request: {}", latlng);

      ResponseEntity<GeocodeResponse> response = restTemplate.getForEntity(url, GeocodeResponse.class);
      GeocodeResponse geocodeResponse = response.getBody();

      if (geocodeResponse == null) {
        throw new BadRequestException("Không thể lấy dữ liệu từ Google Maps API");
      }

      if (!"OK".equals(geocodeResponse.getStatus())) {
        String errorMsg = geocodeResponse.getErrorMessage() != null 
            ? geocodeResponse.getErrorMessage() 
            : "Lỗi từ Google Maps API: " + geocodeResponse.getStatus();
        log.error("Reverse geocoding failed: {}", errorMsg);
        return GeocodeResultResponse.builder()
            .status(geocodeResponse.getStatus())
            .message(errorMsg)
            .build();
      }

      if (geocodeResponse.getResults() == null || geocodeResponse.getResults().isEmpty()) {
        return GeocodeResultResponse.builder()
            .status("ZERO_RESULTS")
            .message("Không tìm thấy địa chỉ cho tọa độ này")
            .build();
      }

      GeocodeResponse.Result firstResult = geocodeResponse.getResults().get(0);

      return GeocodeResultResponse.builder()
          .formattedAddress(firstResult.getFormattedAddress())
          .latitude(request.getLatitude())
          .longitude(request.getLongitude())
          .placeId(firstResult.getPlaceId())
          .status("OK")
          .message("Thành công")
          .build();

    } catch (Exception e) {
      log.error("Error during reverse geocoding: {}", e.getMessage(), e);
      throw new BadRequestException("Lỗi khi lấy địa chỉ từ tọa độ: " + e.getMessage());
    }
  }

  @Override
  public DistanceResultResponse calculateDistance(DistanceRequest request) {
    if (!googleMapsConfig.isEnabled() || googleMapsConfig.getApiKey() == null || googleMapsConfig.getApiKey().isEmpty()) {
      log.warn("Google Maps API is disabled or API key is not configured");
      return DistanceResultResponse.builder()
          .status("DISABLED")
          .message("Google Maps API chưa được kích hoạt hoặc chưa cấu hình API key")
          .build();
    }

    try {
      String origin = request.getOriginLatitude() + "," + request.getOriginLongitude();
      String destination = request.getDestinationLatitude() + "," + request.getDestinationLongitude();

      String url = UriComponentsBuilder
          .fromUriString(googleMapsConfig.getBaseUrl() + "/distancematrix/json")
          .queryParam("origins", origin)
          .queryParam("destinations", destination)
          .queryParam("mode", request.getMode())
          .queryParam("language", request.getLanguage())
          .queryParam("units", request.getUnit())
          .queryParam("key", googleMapsConfig.getApiKey())
          .toUriString();

      log.debug("Distance calculation request: {} to {}", origin, destination);

      ResponseEntity<DistanceMatrixResponse> response = restTemplate.getForEntity(url, DistanceMatrixResponse.class);
      DistanceMatrixResponse distanceResponse = response.getBody();

      if (distanceResponse == null) {
        throw new BadRequestException("Không thể lấy dữ liệu từ Google Maps API");
      }

      if (!"OK".equals(distanceResponse.getStatus())) {
        String errorMsg = distanceResponse.getErrorMessage() != null 
            ? distanceResponse.getErrorMessage() 
            : "Lỗi từ Google Maps API: " + distanceResponse.getStatus();
        log.error("Distance calculation failed: {}", errorMsg);
        return DistanceResultResponse.builder()
            .status(distanceResponse.getStatus())
            .message(errorMsg)
            .build();
      }

      if (distanceResponse.getRows() == null || distanceResponse.getRows().isEmpty() ||
          distanceResponse.getRows().get(0).getElements() == null ||
          distanceResponse.getRows().get(0).getElements().isEmpty()) {
        return DistanceResultResponse.builder()
            .status("ZERO_RESULTS")
            .message("Không thể tính toán khoảng cách")
            .build();
      }

      DistanceMatrixResponse.Row.Element element = distanceResponse.getRows().get(0).getElements().get(0);

      if (!"OK".equals(element.getStatus())) {
        return DistanceResultResponse.builder()
            .status(element.getStatus())
            .message("Không thể tính toán khoảng cách: " + element.getStatus())
            .build();
      }

      String originAddress = distanceResponse.getOriginAddresses() != null && !distanceResponse.getOriginAddresses().isEmpty()
          ? distanceResponse.getOriginAddresses().get(0)
          : origin;
      String destinationAddress = distanceResponse.getDestinationAddresses() != null && !distanceResponse.getDestinationAddresses().isEmpty()
          ? distanceResponse.getDestinationAddresses().get(0)
          : destination;

      return DistanceResultResponse.builder()
          .originAddress(originAddress)
          .destinationAddress(destinationAddress)
          .distanceText(element.getDistance() != null ? element.getDistance().getText() : null)
          .distanceMeters(element.getDistance() != null ? element.getDistance().getValue() : null)
          .durationText(element.getDuration() != null ? element.getDuration().getText() : null)
          .durationSeconds(element.getDuration() != null ? element.getDuration().getValue() : null)
          .status("OK")
          .message("Thành công")
          .build();

    } catch (Exception e) {
      log.error("Error during distance calculation: {}", e.getMessage(), e);
      throw new BadRequestException("Lỗi khi tính toán khoảng cách: " + e.getMessage());
    }
  }

  @Override
  public GeocodeResultResponse getCoordinatesFromAddress(String address) {
    if (address == null || address.trim().isEmpty()) {
      return GeocodeResultResponse.builder()
          .status("INVALID_REQUEST")
          .message("Địa chỉ không được để trống")
          .build();
    }

    GeocodeRequest request = new GeocodeRequest();
    request.setAddress(address);
    return geocode(request);
  }
}

