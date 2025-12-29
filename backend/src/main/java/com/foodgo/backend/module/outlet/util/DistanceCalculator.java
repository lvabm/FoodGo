package com.foodgo.backend.module.outlet.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for calculating distances between coordinates
 * Uses Haversine formula for great-circle distance
 */
public class DistanceCalculator {

  private static final double EARTH_RADIUS_KM = 6371.0;

  private DistanceCalculator() {
    // Utility class
  }

  /**
   * Calculate distance between two points using Haversine formula
   * @param lat1 Latitude of first point
   * @param lon1 Longitude of first point
   * @param lat2 Latitude of second point
   * @param lon2 Longitude of second point
   * @return Distance in kilometers
   */
  public static double calculateDistanceKm(
      BigDecimal lat1, BigDecimal lon1,
      BigDecimal lat2, BigDecimal lon2) {
    if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
      return Double.MAX_VALUE; // Return max value if coordinates are missing
    }

    double lat1Rad = Math.toRadians(lat1.doubleValue());
    double lon1Rad = Math.toRadians(lon1.doubleValue());
    double lat2Rad = Math.toRadians(lat2.doubleValue());
    double lon2Rad = Math.toRadians(lon2.doubleValue());

    double deltaLat = lat2Rad - lat1Rad;
    double deltaLon = lon2Rad - lon1Rad;

    double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
        + Math.cos(lat1Rad) * Math.cos(lat2Rad)
        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c;
  }

  /**
   * Calculate distance in meters
   */
  public static int calculateDistanceMeters(
      BigDecimal lat1, BigDecimal lon1,
      BigDecimal lat2, BigDecimal lon2) {
    double distanceKm = calculateDistanceKm(lat1, lon1, lat2, lon2);
    return (int) Math.round(distanceKm * 1000);
  }

  /**
   * Format distance as human-readable string
   */
  public static String formatDistance(double distanceKm) {
    if (distanceKm < 1) {
      int meters = (int) Math.round(distanceKm * 1000);
      return meters + " m";
    } else {
      BigDecimal distance = BigDecimal.valueOf(distanceKm)
          .setScale(1, RoundingMode.HALF_UP);
      return distance + " km";
    }
  }

  /**
   * Check if a point is within radius of another point
   */
  public static boolean isWithinRadius(
      BigDecimal lat1, BigDecimal lon1,
      BigDecimal lat2, BigDecimal lon2,
      double radiusKm) {
    double distance = calculateDistanceKm(lat1, lon1, lat2, lon2);
    return distance <= radiusKm;
  }
}


