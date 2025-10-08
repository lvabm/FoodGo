package com.foodgo.backend.common.constant;

public class AppConstants {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final long JWT_EXPIRATION = 3600000L; // 1 hour
    public static final long REFRESH_TOKEN_EXPIRATION = 604800000L; // 7 days
    public static final String DEFAULT_AVATAR_URL = "https://default-avatar.png";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private AppConstants() {}
}
