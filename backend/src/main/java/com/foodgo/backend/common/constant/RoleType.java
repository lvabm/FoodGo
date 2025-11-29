package com.foodgo.backend.common.constant;

import lombok.Getter;

@Getter
public enum RoleType {
  USER("ROLE_USER"),
  OWNER("ROLE_OWNER"),
  ADMIN("ROLE_ADMIN"),
  SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN"),
  GUEST("ROLE_GUEST");

  private final String name;

  RoleType(String name) {
    this.name = name;
  }
}
