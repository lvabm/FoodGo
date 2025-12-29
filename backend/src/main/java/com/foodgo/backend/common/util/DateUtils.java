package com.foodgo.backend.common.util;

import com.foodgo.backend.common.constant.AppConstants;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date and time operations
 */
public class DateUtils {

  private static final DateTimeFormatter DATE_FORMATTER = 
      DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
  private static final DateTimeFormatter TIME_FORMATTER = 
      DateTimeFormatter.ofPattern(AppConstants.TIME_FORMAT);
  private static final DateTimeFormatter DATETIME_FORMATTER = 
      DateTimeFormatter.ofPattern(AppConstants.DATETIME_FORMAT);

  private DateUtils() {
    // Utility class
  }

  /**
   * Format LocalDate to string using default format (dd-MM-yyyy)
   */
  public static String formatDate(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.format(DATE_FORMATTER);
  }

  /**
   * Format LocalTime to string using default format (HH:mm:ss)
   */
  public static String formatTime(LocalTime time) {
    if (time == null) {
      return null;
    }
    return time.format(TIME_FORMATTER);
  }

  /**
   * Format LocalDateTime to string using default format
   */
  public static String formatDateTime(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.format(DATETIME_FORMATTER);
  }

  /**
   * Parse string to LocalDate using default format (dd-MM-yyyy)
   */
  public static LocalDate parseDate(String dateString) {
    if (dateString == null || dateString.trim().isEmpty()) {
      return null;
    }
    try {
      return LocalDate.parse(dateString.trim(), DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      // Try ISO format as fallback
      try {
        return LocalDate.parse(dateString.trim());
      } catch (DateTimeParseException ex) {
        throw new IllegalArgumentException("Invalid date format: " + dateString, ex);
      }
    }
  }

  /**
   * Parse string to LocalTime using default format (HH:mm:ss)
   */
  public static LocalTime parseTime(String timeString) {
    if (timeString == null || timeString.trim().isEmpty()) {
      return null;
    }
    try {
      return LocalTime.parse(timeString.trim(), TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      // Try ISO format as fallback
      try {
        return LocalTime.parse(timeString.trim());
      } catch (DateTimeParseException ex) {
        throw new IllegalArgumentException("Invalid time format: " + timeString, ex);
      }
    }
  }

  /**
   * Get current date in Vietnam timezone
   */
  public static LocalDate getCurrentDate() {
    return LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  /**
   * Get current time in Vietnam timezone
   */
  public static LocalTime getCurrentTime() {
    return LocalTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  /**
   * Get current date-time in Vietnam timezone
   */
  public static LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  /**
   * Check if date is today
   */
  public static boolean isToday(LocalDate date) {
    if (date == null) {
      return false;
    }
    return date.equals(getCurrentDate());
  }

  /**
   * Check if date is in the past
   */
  public static boolean isPast(LocalDate date) {
    if (date == null) {
      return false;
    }
    return date.isBefore(getCurrentDate());
  }

  /**
   * Check if date is in the future
   */
  public static boolean isFuture(LocalDate date) {
    if (date == null) {
      return false;
    }
    return date.isAfter(getCurrentDate());
  }

  /**
   * Get days between two dates
   */
  public static long daysBetween(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      return 0;
    }
    return java.time.temporal.ChronoUnit.DAYS.between(start, end);
  }

  /**
   * Get hours between two date-times
   */
  public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) {
      return 0;
    }
    return java.time.temporal.ChronoUnit.HOURS.between(start, end);
  }

  /**
   * Convert Instant to LocalDateTime in Vietnam timezone
   */
  public static LocalDateTime toLocalDateTime(Instant instant) {
    if (instant == null) {
      return null;
    }
    return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  /**
   * Convert LocalDateTime to Instant in Vietnam timezone
   */
  public static Instant toInstant(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
  }
}


