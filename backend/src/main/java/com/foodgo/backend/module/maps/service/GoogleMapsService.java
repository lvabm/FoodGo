package com.foodgo.backend.module.maps.service;

import com.foodgo.backend.module.maps.dto.request.DistanceRequest;
import com.foodgo.backend.module.maps.dto.request.GeocodeRequest;
import com.foodgo.backend.module.maps.dto.request.ReverseGeocodeRequest;
import com.foodgo.backend.module.maps.dto.response.DistanceResultResponse;
import com.foodgo.backend.module.maps.dto.response.GeocodeResultResponse;

/**
 * Service interface for Google Maps operations
 */
public interface GoogleMapsService {

  /**
   * Geocode: Convert address to coordinates
   */
  GeocodeResultResponse geocode(GeocodeRequest request);

  /**
   * Reverse Geocode: Convert coordinates to address
   */
  GeocodeResultResponse reverseGeocode(ReverseGeocodeRequest request);

  /**
   * Calculate distance and duration between two points
   */
  DistanceResultResponse calculateDistance(DistanceRequest request);

  /**
   * Auto-fill coordinates from address (helper method)
   */
  GeocodeResultResponse getCoordinatesFromAddress(String address);
}


