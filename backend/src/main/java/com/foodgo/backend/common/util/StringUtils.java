package com.foodgo.backend.common.util;

/**
 * Utility class for string operations
 */
public class StringUtils {

  private StringUtils() {
    // Utility class
  }

  /**
   * Check if string is blank (null, empty, or whitespace only)
   */
  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  /**
   * Check if string is not blank
   */
  public static boolean isNotBlank(String str) {
    return str != null && !str.trim().isEmpty();
  }

  /**
   * Trim string, return null if null
   */
  public static String trim(String str) {
    return str == null ? null : str.trim();
  }

  /**
   * Trim string, return empty string if null
   */
  public static String trimToEmpty(String str) {
    return str == null ? "" : str.trim();
  }

  /**
   * Get default value if string is blank
   */
  public static String defaultIfBlank(String str, String defaultValue) {
    return isBlank(str) ? defaultValue : str;
  }

  /**
   * Capitalize first letter
   */
  public static String capitalize(String str) {
    if (isBlank(str)) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

  /**
   * Truncate string to max length
   */
  public static String truncate(String str, int maxLength) {
    if (str == null) {
      return null;
    }
    if (str.length() <= maxLength) {
      return str;
    }
    return str.substring(0, maxLength) + "...";
  }

  /**
   * Mask sensitive information (e.g., email, phone)
   */
  public static String mask(String str, int visibleStart, int visibleEnd) {
    if (isBlank(str) || str.length() <= visibleStart + visibleEnd) {
      return "***";
    }
    String start = str.substring(0, visibleStart);
    String end = str.substring(str.length() - visibleEnd);
    String middle = "*".repeat(Math.max(0, str.length() - visibleStart - visibleEnd));
    return start + middle + end;
  }

  /**
   * Mask email (show first 2 chars and domain)
   */
  public static String maskEmail(String email) {
    if (isBlank(email) || !email.contains("@")) {
      return "***";
    }
    int atIndex = email.indexOf("@");
    String localPart = email.substring(0, atIndex);
    String domain = email.substring(atIndex);
    
    if (localPart.length() <= 2) {
      return "**" + domain;
    }
    return localPart.substring(0, 2) + "***" + domain;
  }

  /**
   * Mask phone number (show last 4 digits)
   */
  public static String maskPhone(String phone) {
    if (isBlank(phone) || phone.length() <= 4) {
      return "***";
    }
    return "***" + phone.substring(phone.length() - 4);
  }
}


