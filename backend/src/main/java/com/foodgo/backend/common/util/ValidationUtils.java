package com.foodgo.backend.common.util;

import java.util.regex.Pattern;

public class ValidationUtils {

  // Email pattern
  private static final Pattern EMAIL_PATTERN = 
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  // Vietnamese phone pattern (10-11 digits, may start with 0 or +84)
  private static final Pattern PHONE_PATTERN = 
      Pattern.compile("^(\\+84|0)[0-9]{9,10}$");

  // Price range pattern (e.g., "100000-200000", "100k-200k", "100-200")
  private static final Pattern PRICE_RANGE_PATTERN = 
      Pattern.compile("^[0-9]+([kK]?)-[0-9]+([kK]?)$");

  /**
   * Validate email format
   */
  public static boolean isValidEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return EMAIL_PATTERN.matcher(email.trim()).matches();
  }

  /**
   * Validate Vietnamese phone number format
   */
  public static boolean isValidPhone(String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      return false;
    }
    // Remove spaces and dashes
    String cleaned = phone.replaceAll("[\\s-]", "");
    return PHONE_PATTERN.matcher(cleaned).matches();
  }

  /**
   * Validate price range format
   */
  public static boolean isValidPriceRange(String priceRange) {
    if (priceRange == null || priceRange.trim().isEmpty()) {
      return true; // Optional field
    }
    return PRICE_RANGE_PATTERN.matcher(priceRange.trim()).matches();
  }

  /**
   * Validate capacity (must be positive)
   */
  public static boolean isValidCapacity(Integer capacity) {
    return capacity == null || capacity > 0;
  }

  /**
   * Validate URL format
   */
  public static boolean isValidUrl(String url) {
    if (url == null || url.trim().isEmpty()) {
      return false;
    }
    try {
      new java.net.URL(url.trim());
      return true;
    } catch (java.net.MalformedURLException e) {
      return false;
    }
  }

  /**
   * Validate that string is not blank
   */
  public static boolean isNotBlank(String str) {
    return str != null && !str.trim().isEmpty();
  }

  /**
   * Validate that string is blank
   */
  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  /**
   * Normalize phone number (remove spaces, dashes, convert +84 to 0)
   */
  public static String normalizePhone(String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      return null;
    }
    String cleaned = phone.replaceAll("[\\s-]", "");
    if (cleaned.startsWith("+84")) {
      cleaned = "0" + cleaned.substring(3);
    }
    return cleaned;
  }

  /**
   * Validate and normalize email (trim and lowercase)
   */
  public static String normalizeEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return null;
    }
    return email.trim().toLowerCase();
  }

  /**
   * Validate password strength
   * Requirements: at least 8 characters, contains uppercase, lowercase, digit
   */
  public static boolean isValidPassword(String password) {
    if (password == null || password.length() < 8) {
      return false;
    }
    boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
    boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
    boolean hasDigit = password.chars().anyMatch(Character::isDigit);
    return hasUpper && hasLower && hasDigit;
  }

  /**
   * Validate booking time is in the future
   */
  public static boolean isFutureDateTime(java.time.LocalDate date, java.time.LocalTime time) {
    if (date == null || time == null) {
      return false;
    }
    java.time.LocalDateTime dateTime = java.time.LocalDateTime.of(date, time);
    return dateTime.isAfter(java.time.LocalDateTime.now());
  }

  /**
   * Validate rating is within valid range (1-5)
   */
  public static boolean isValidRating(Integer rating) {
    return rating != null && rating >= 1 && rating <= 5;
  }

  /**
   * Validate capacity is positive and reasonable (max 1000)
   */
  public static boolean isValidCapacityRange(Integer capacity) {
    return capacity != null && capacity > 0 && capacity <= 1000;
  }
}

