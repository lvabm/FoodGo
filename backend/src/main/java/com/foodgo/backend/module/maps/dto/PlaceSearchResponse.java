package com.foodgo.backend.module.maps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response from Google Maps Places API (Text Search)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceSearchResponse {
  private String status;
  private List<Result> results;
  private String nextPageToken;
  private String errorMessage;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Result {
    private String placeId;
    private String name;
    private String formattedAddress;
    private Geometry geometry;
    private String icon;
    private String vicinity;
    private List<String> types;
    private Double rating;
    private Integer userRatingsTotal;
    private OpeningHours openingHours;
    private String businessStatus;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
      private Location location;
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
    public static class OpeningHours {
      private Boolean openNow;
      private List<String> weekdayText;
    }
  }
}


