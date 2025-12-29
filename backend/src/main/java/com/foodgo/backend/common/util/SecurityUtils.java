package com.foodgo.backend.common.util;

import java.util.regex.Pattern;

/**
 * Utility class for security operations
 */
public class SecurityUtils {

  // Pattern to detect potential XSS attacks
  private static final Pattern XSS_PATTERN = Pattern.compile(
      "(?i)(<script|javascript:|onerror=|onload=|onclick=|onmouseover=|onfocus=|onblur=|<iframe|data:text/html)"
  );

  // Pattern to detect SQL injection attempts
  private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
      "(?i)(union|select|insert|update|delete|drop|create|alter|exec|execute|script|--|;|'|\"|/\\*|\\*/)"
  );

  // Pattern to detect path traversal attempts
  private static final Pattern PATH_TRAVERSAL_PATTERN = Pattern.compile(
      "(\\.\\./|\\.\\.\\\\|/etc/passwd|/etc/shadow|C:\\\\|/windows/)"
  );

  private SecurityUtils() {
    // Utility class
  }

  /**
   * Sanitize string to prevent XSS attacks
   */
  public static String sanitizeInput(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }

    // Remove potential XSS patterns
    String sanitized = XSS_PATTERN.matcher(input).replaceAll("");
    
    // HTML escape common characters
    sanitized = sanitized
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#x27;")
        .replace("/", "&#x2F;");

    return sanitized.trim();
  }

  /**
   * Check if input contains potential XSS
   */
  public static boolean containsXSS(String input) {
    if (input == null || input.isEmpty()) {
      return false;
    }
    return XSS_PATTERN.matcher(input).find();
  }

  /**
   * Check if input contains potential SQL injection
   */
  public static boolean containsSQLInjection(String input) {
    if (input == null || input.isEmpty()) {
      return false;
    }
    // Note: This is a basic check. JPA/Hibernate already provides protection,
    // but this can help detect suspicious inputs
    return SQL_INJECTION_PATTERN.matcher(input).find();
  }

  /**
   * Check if input contains path traversal attempts
   */
  public static boolean containsPathTraversal(String input) {
    if (input == null || input.isEmpty()) {
      return false;
    }
    return PATH_TRAVERSAL_PATTERN.matcher(input).find();
  }

  /**
   * Sanitize filename to prevent path traversal
   */
  public static String sanitizeFilename(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "file";
    }

    // Remove path traversal attempts
    String sanitized = filename
        .replaceAll("\\.\\.", "")
        .replaceAll("/", "_")
        .replaceAll("\\\\", "_")
        .replaceAll("[^a-zA-Z0-9._-]", "_");

    if (sanitized.isEmpty() || sanitized.equals(".")) {
      return "file";
    }

    return sanitized;
  }

  /**
   * Validate and sanitize user input
   */
  public static String validateAndSanitize(String input, boolean allowHtml) {
    if (input == null || input.isEmpty()) {
      return input;
    }

    // Check for XSS
    if (containsXSS(input)) {
      throw new IllegalArgumentException("Input contains potentially dangerous content.");
    }

    // Check for path traversal
    if (containsPathTraversal(input)) {
      throw new IllegalArgumentException("Input contains potentially dangerous path characters.");
    }

    // Sanitize if HTML is not allowed
    if (!allowHtml) {
      return sanitizeInput(input);
    }

    return input.trim();
  }

  /**
   * Mask sensitive data for logging
   */
  public static String maskSensitiveData(String data) {
    if (data == null || data.isEmpty()) {
      return "***";
    }
    if (data.length() <= 4) {
      return "***";
    }
    return "***" + data.substring(data.length() - 4);
  }
}


