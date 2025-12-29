package com.foodgo.backend.module.maps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Response from Google Maps Distance Matrix API
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixResponse {
  private String status;
  private List<String> originAddresses;
  private List<String> destinationAddresses;
  private List<Row> rows;
  private String errorMessage;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Row {
    private List<Element> elements;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Element {
      private String status;
      private Distance distance;
      private Duration duration;
      private Duration durationInTraffic;

      @Data
      @JsonIgnoreProperties(ignoreUnknown = true)
      public static class Distance {
        private String text;
        private Integer value; // in meters
      }

      @Data
      @JsonIgnoreProperties(ignoreUnknown = true)
      public static class Duration {
        private String text;
        private Integer value; // in seconds
      }
    }
  }
}


