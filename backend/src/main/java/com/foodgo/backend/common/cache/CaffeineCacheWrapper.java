package com.foodgo.backend.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * Wrapper class to adapt Caffeine Cache to Spring Cache interface
 */
public class CaffeineCacheWrapper extends AbstractValueAdaptingCache {

  @NonNull
  private final String name;
  @NonNull
  private final Cache<Object, Object> cache;

  public CaffeineCacheWrapper(@NonNull String name, @NonNull Cache<Object, Object> cache) {
    super(true); // Allow null values
    this.name = name;
    this.cache = cache;
  }

  @Override
  @NonNull
  public String getName() {
    return this.name;
  }

  @Override
  @NonNull
  public Object getNativeCache() {
    return this.cache;
  }

  @Override
  @Nullable
  protected Object lookup(@NonNull Object key) {
    return cache.getIfPresent(key);
  }

  @Override
  @NonNull
  public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
    try {
      @SuppressWarnings("unchecked")
      T value = (T) cache.get(key, k -> {
        try {
          return valueLoader.call();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });
      return value;
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RuntimeException) {
        throw (RuntimeException) e.getCause();
      }
      throw e;
    }
  }

  @Override
  public void put(@NonNull Object key, @Nullable Object value) {
    cache.put(key, value);
  }

  @Override
  public void evict(@NonNull Object key) {
    cache.invalidate(key);
  }

  @Override
  public void clear() {
    cache.invalidateAll();
  }
}

