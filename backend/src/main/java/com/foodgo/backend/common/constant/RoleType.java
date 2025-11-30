package com.foodgo.backend.common.constant;

import lombok.Getter;

@Getter
public enum RoleType {
  ROLE_USER("ROLE_USER"),
  ROLE_OWNER("ROLE_OWNER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN"),
  ROLE_GUEST("ROLE_GUEST");

  private final String name;

  RoleType(String name) {
    this.name = name;
  }
}
