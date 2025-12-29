package com.foodgo.backend.module.maps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response from Google Maps Geocoding API
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {
  private String status;
  private List<Result> results;
  private String errorMessage;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Result {
    private String formattedAddress;
    private Geometry geometry;
    private List<AddressComponent> addressComponents;
    private String placeId;
    private List<String> types;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
      private Location location;
      private String locationType;
      private Viewport viewport;

      @Data
      @JsonIgnoreProperties(ignoreUnknown = true)
      public static class Location {
        private BigDecimal lat;
        private BigDecimal lng;
      }

      @Data
      @JsonIgnoreProperties(ignoreUnknown = true)
      public static class Viewport {
        private Location northeast;
        private Location southwest;
      }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressComponent {
      private String longName;
      private String shortName;
      private List<String> types;
    }
  }
}


