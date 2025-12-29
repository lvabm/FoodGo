package com.foodgo.backend.module.maps.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Google Maps API
 */
@Configuration
@ConfigurationProperties(prefix = "google.maps")
@Getter
@Setter
public class GoogleMapsConfig {

  /**
   * Google Maps API Key
   * Get from: https://console.cloud.google.com/google/maps-apis
   */
  private String apiKey;

  /**
   * Google Maps API Base URL
   */
  private String baseUrl = "https://maps.googleapis.com/maps/api";

  /**
   * Enable/disable Google Maps features
   */
  private boolean enabled = true;

  /**
   * Timeout for API calls (milliseconds)
   */
  private int timeout = 5000;
}


