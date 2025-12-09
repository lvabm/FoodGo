package com.foodgo.backend.module.outlet.dto.request;

import java.math.BigDecimal;
import java.util.Optional;

public record OutletUpdateRequest(
    String name,
    String description,
    String address,
    String email,
    String phoneNumber,
    String website,
    BigDecimal latitude,
    BigDecimal longitude,
    String priceRange,
    Integer capacity,
    // ID ngoại lai (cũng là Optional)
    Integer typeId,
    Integer districtId) {
  // Phương thức tiện ích cho các trường String
  public Optional<String> optionalName() {
    return Optional.ofNullable(name).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalDescription() {
    return Optional.ofNullable(description).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalAddress() {
    return Optional.ofNullable(address).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalEmail() {
    return Optional.ofNullable(email).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalPhoneNumber() {
    return Optional.ofNullable(phoneNumber).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalWebsite() {
    return Optional.ofNullable(website).filter(s -> !s.isBlank());
  }

  public Optional<String> optionalPriceRange() {
    return Optional.ofNullable(priceRange).filter(s -> !s.isBlank());
  }

  // Phương thức tiện ích cho các trường Số (BigDecimal, Integer)
  public Optional<BigDecimal> optionalLatitude() {
    return Optional.ofNullable(latitude);
  }

  public Optional<BigDecimal> optionalLongitude() {
    return Optional.ofNullable(longitude);
  }

  public Optional<Integer> optionalCapacity() {
    return Optional.ofNullable(capacity);
  }

  // Phương thức tiện ích cho các ID ngoại lai
  public Optional<Integer> optionalTypeId() {
    return Optional.ofNullable(typeId);
  }

  public Optional<Integer> optionalDistrictId() {
    return Optional.ofNullable(districtId);
  }
}
