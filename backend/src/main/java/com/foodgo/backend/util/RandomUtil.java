package com.foodgo.backend.util;

import java.util.UUID;

public class RandomUtil {
  private static final String PREFIX = "foodgo_";

  public static String generateUniqueUsername() {
    String randomSuffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    return PREFIX + randomSuffix;
  }
}
