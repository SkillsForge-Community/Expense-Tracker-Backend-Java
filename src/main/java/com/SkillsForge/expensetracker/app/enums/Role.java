package com.SkillsForge.expensetracker.app.enums;

import java.util.Set;

public enum Role {
  USER,
  ADMIN;

  /**
   * Get the permissions associated with this role
   *
   * @return set of permissions for this role
   */
  public Set<Permissions> getPermissions() {
    return switch (this) {
      case ADMIN -> Set.of(Permissions.values()); // Admin has all permissions
      case USER -> Set.of(
          Permissions.TRANSACTION_READ,
          Permissions.TRANSACTION_WRITE,
          Permissions.TRANSACTION_DELETE,
          Permissions.USER_READ,
          Permissions.USER_WRITE);
    };
  }
}
