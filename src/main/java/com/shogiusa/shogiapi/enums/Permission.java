package com.shogiusa.shogiapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing various permissions in the system.
 * <p>
 * Each permission is associated with a specific operation or set of operations
 * that can be performed within the application. These permissions are used to
 * control access to different parts of the system based on the roles assigned
 * to a user.
 */
@RequiredArgsConstructor
public enum Permission {

    /**
     * Permission for reading admin-related resources.
     */
    ADMIN_READ("admin:read"),

    /**
     * Permission for updating admin-related resources.
     */
    ADMIN_UPDATE("admin:update"),

    /**
     * Permission for creating admin-related resources.
     */
    ADMIN_CREATE("admin:create"),

    /**
     * Permission for deleting admin-related resources.
     */
    ADMIN_DELETE("admin:delete"),

    /**
     * Permission for reading manager-related resources.
     */
    MANAGER_READ("management:read"),

    /**
     * Permission for updating manager-related resources.
     */
    MANAGER_UPDATE("management:update"),

    /**
     * Permission for creating manager-related resources.
     */
    MANAGER_CREATE("management:create"),

    /**
     * Permission for deleting manager-related resources.
     */
    MANAGER_DELETE("management:delete");

    /**
     * The specific permission string.
     */
    @Getter
    private final String permission;
}