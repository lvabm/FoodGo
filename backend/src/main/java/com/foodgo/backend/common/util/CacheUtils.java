package com.foodgo.backend.common.util;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Utility class for cache operations
 */
public class CacheUtils {

  private CacheUtils() {
    // Utility class
  }

  /**
   * Clear specific cache
   */
  public static void clearCache(@Nullable CacheManager cacheManager, @NonNull String cacheName) {
    if (cacheManager != null) {
      Cache cache = cacheManager.getCache(cacheName);
      if (cache != null) {
        cache.clear();
      }
    }
  }

  /**
   * Clear all caches
   */
  public static void clearAllCaches(@Nullable CacheManager cacheManager) {
    if (cacheManager != null) {
      cacheManager.getCacheNames().forEach(cacheName -> {
        if (cacheName != null) {
          Cache cache = cacheManager.getCache(cacheName);
          if (cache != null) {
            cache.clear();
          }
        }
      });
    }
  }

  /**
   * Evict cache entry by key
   */
  public static void evictCacheEntry(@Nullable CacheManager cacheManager, @NonNull String cacheName, @NonNull Object key) {
    if (cacheManager != null) {
      Cache cache = cacheManager.getCache(cacheName);
      if (cache != null) {
        cache.evict(key);
      }
    }
  }

  /**
   * Get cache statistics (if available)
   */
  @NonNull
  public static String getCacheStats(@Nullable CacheManager cacheManager, @NonNull String cacheName) {
    if (cacheManager == null) {
      return "CacheManager not available";
    }

    Cache cache = cacheManager.getCache(cacheName);
    if (cache == null) {
      return "Cache not found: " + cacheName;
    }

    // For Caffeine cache, we can get stats
    Object nativeCache = cache.getNativeCache();
    if (nativeCache instanceof com.github.benmanes.caffeine.cache.Cache) {
      com.github.benmanes.caffeine.cache.Cache<?, ?> caffeineCache = 
          (com.github.benmanes.caffeine.cache.Cache<?, ?>) nativeCache;
      com.github.benmanes.caffeine.cache.stats.CacheStats stats = caffeineCache.stats();
      return String.format(
          "Cache: %s | Hits: %d | Misses: %d | Hit Rate: %.2f%% | Size: %d",
          cacheName,
          stats.hitCount(),
          stats.missCount(),
          stats.hitRate() * 100,
          caffeineCache.estimatedSize()
      );
    }

    return "Cache: " + cacheName + " | Stats not available (using ConcurrentMapCache)";
  }
}

