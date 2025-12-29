package com.foodgo.backend.common.constant;

public class AppConstants {
  // Pagination
  public static final int DEFAULT_PAGE_SIZE = 20;
  public static final int MAX_PAGE_SIZE = 100;
  
  // JWT
  public static final long JWT_EXPIRATION = 3600000L; // 1 hour
  public static final long REFRESH_TOKEN_EXPIRATION = 604800000L; // 7 days
  
  // Booking
  public static final int MAX_BOOKING_DAYS_AHEAD = 30; // Tối đa đặt trước 30 ngày
  public static final int MIN_BOOKING_HOURS_AHEAD = 2; // Phải đặt trước ít nhất 2 giờ
  public static final int MIN_CANCELLATION_HOURS_BEFORE = 2; // Phải hủy trước ít nhất 2 giờ so với giờ đặt bàn
  public static final int MAX_PENDING_BOOKINGS_PER_USER = 5; // Tối đa 5 booking đang chờ duyệt
  public static final int MIN_NUMBER_OF_GUESTS = 1; // Số khách tối thiểu
  public static final int MAX_NUMBER_OF_GUESTS = 50; // Số khách tối đa
  
  // Review
  public static final int MAX_REVIEW_DAYS_AFTER_COMPLETED = 30; // Chỉ được review trong 30 ngày sau khi completed
  public static final int REVIEW_UPDATE_HOURS_LIMIT = 24; // Chỉ được sửa review trong 24 giờ sau khi tạo
  
  // Notification
  public static final int NOTIFICATION_BATCH_SIZE = 50; // Số lượng notifications xử lý trong một batch
  public static final int NOTIFICATION_RETENTION_DAYS = 90; // Giữ notifications trong 90 ngày
  
  // Cache
  public static final int CACHE_MAX_SIZE = 2000; // Maximum cache entries
  public static final int CACHE_TTL_HOURS = 2; // Cache TTL in hours
  public static final int CACHE_ACCESS_TTL_HOURS = 1; // Cache access TTL in hours
  
  // Async Processing
  public static final int ASYNC_CORE_POOL_SIZE = 2; // Core thread pool size for async tasks
  public static final int ASYNC_MAX_POOL_SIZE = 5; // Max thread pool size for async tasks
  public static final int ASYNC_QUEUE_CAPACITY = 100; // Queue capacity for async tasks
  
  // Rate Limiting (for future implementation)
  public static final int RATE_LIMIT_REQUESTS_PER_MINUTE = 60; // Requests per minute per user
  public static final int RATE_LIMIT_REQUESTS_PER_HOUR = 1000; // Requests per hour per user
  
  // Defaults
  public static final String DEFAULT_AVATAR_URL = "https://default-avatar.png";
  
  // Date/Time Formats
  public static final String DATE_FORMAT = "dd-MM-yyyy";
  public static final String TIME_FORMAT = "HH:mm:ss";
  public static final String DATETIME_FORMAT = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";

  private AppConstants() {}
}
