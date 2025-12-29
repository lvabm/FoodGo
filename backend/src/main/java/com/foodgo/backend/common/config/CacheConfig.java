package com.foodgo.backend.common.config;

import com.foodgo.backend.common.cache.CaffeineCacheWrapper;
import com.foodgo.backend.common.constant.AppConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

  // Cache names
  public static final String CACHE_COUNTRIES = "countries";
  public static final String CACHE_PROVINCES = "provinces";
  public static final String CACHE_DISTRICTS = "districts";
  public static final String CACHE_OUTLETS = "outlets";
  public static final String CACHE_OUTLET_TYPES = "outletTypes";
  public static final String CACHE_OUTLET_CATEGORIES = "outletCategories";
  public static final String CACHE_MENU_ITEMS = "menuItems";
  public static final String CACHE_MEMBERSHIP_PLANS = "membershipPlans";

  @Bean
  @Profile("dev")
  public CacheManager devCacheManager() {
    // Simple in-memory cache for development
    ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
    cacheManager.setCacheNames(List.of(
        CACHE_OUTLETS,
        CACHE_OUTLET_TYPES,
        CACHE_OUTLET_CATEGORIES,
        CACHE_COUNTRIES,
        CACHE_PROVINCES,
        CACHE_DISTRICTS,
        CACHE_MENU_ITEMS,
        CACHE_MEMBERSHIP_PLANS
    ));
    return cacheManager;
  }

  @Bean
  @Profile("prod")  
  public CacheManager prodCacheManager() {
    // Caffeine cache for production with TTL and size limits
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    
    // Cấu hình Caffeine builder với các tham số tối ưu từ AppConstants
    Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
        .maximumSize(AppConstants.CACHE_MAX_SIZE)
        .expireAfterWrite(AppConstants.CACHE_TTL_HOURS, TimeUnit.HOURS)
        .expireAfterAccess(AppConstants.CACHE_ACCESS_TTL_HOURS, TimeUnit.HOURS)
        .recordStats(); // Enable cache statistics for monitoring
    
    // Tạo các cache instances với Caffeine
    List<Cache> caches = Arrays.asList(
        new CaffeineCacheWrapper(CACHE_OUTLETS, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_OUTLET_TYPES, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_OUTLET_CATEGORIES, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_COUNTRIES, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_PROVINCES, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_DISTRICTS, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_MENU_ITEMS, caffeineBuilder.build()),
        new CaffeineCacheWrapper(CACHE_MEMBERSHIP_PLANS, caffeineBuilder.build())
    );
    
    cacheManager.setCaches(caches);
    return cacheManager;
  }
}

