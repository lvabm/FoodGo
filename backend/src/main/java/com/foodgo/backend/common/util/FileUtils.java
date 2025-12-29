package com.foodgo.backend.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for file operations
 */
public class FileUtils {

  // Allowed image MIME types
  private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
      "image/jpeg",
      "image/jpg",
      "image/png",
      "image/gif",
      "image/webp"
  );

  // Allowed image file extensions
  private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
      ".jpg", ".jpeg", ".png", ".gif", ".webp"
  );

  // Max file size: 5MB
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB in bytes

  // Max image size: 10MB
  private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB in bytes

  private FileUtils() {
    // Utility class
  }

  /**
   * Validate image file
   */
  public static boolean isValidImageFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return false;
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
      return false;
    }

    String filename = file.getOriginalFilename();
    if (filename == null) {
      return false;
    }

    String extension = getFileExtension(filename).toLowerCase();
    if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
      return false;
    }

    return file.getSize() <= MAX_IMAGE_SIZE;
  }

  /**
   * Validate file size
   */
  public static boolean isValidFileSize(MultipartFile file, long maxSize) {
    if (file == null || file.isEmpty()) {
      return false;
    }
    return file.getSize() <= maxSize;
  }

  /**
   * Get file extension
   */
  public static String getFileExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }
    int lastDot = filename.lastIndexOf('.');
    if (lastDot == -1 || lastDot == filename.length() - 1) {
      return "";
    }
    return filename.substring(lastDot);
  }

  /**
   * Get file name without extension
   */
  public static String getFileNameWithoutExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }
    int lastDot = filename.lastIndexOf('.');
    if (lastDot == -1) {
      return filename;
    }
    return filename.substring(0, lastDot);
  }

  /**
   * Sanitize filename (remove dangerous characters)
   */
  public static String sanitizeFilename(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "file";
    }
    // Remove path traversal attempts and dangerous characters
    String sanitized = filename
        .replaceAll("\\.\\.", "") // Remove ..
        .replaceAll("/", "_") // Replace / with _
        .replaceAll("\\\\", "_") // Replace \ with _
        .replaceAll("[^a-zA-Z0-9._-]", "_"); // Replace special chars with _
    
    // Ensure filename is not empty
    if (sanitized.isEmpty() || sanitized.equals(".")) {
      return "file";
    }
    
    return sanitized;
  }

  /**
   * Get max file size for images
   */
  public static long getMaxImageSize() {
    return MAX_IMAGE_SIZE;
  }

  /**
   * Get max file size for general files
   */
  public static long getMaxFileSize() {
    return MAX_FILE_SIZE;
  }

  /**
   * Format file size to human readable string
   */
  public static String formatFileSize(long bytes) {
    if (bytes < 1024) {
      return bytes + " B";
    } else if (bytes < 1024 * 1024) {
      return String.format("%.2f KB", bytes / 1024.0);
    } else if (bytes < 1024 * 1024 * 1024) {
      return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    } else {
      return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
  }
}


