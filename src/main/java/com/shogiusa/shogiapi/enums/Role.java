package com.shogiusa.shogiapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.shogiusa.shogiapi.enums.Permission.*;

/**
 * Enum representing different roles in the system.
 * <p>
 * Each role is associated with a specific set of permissions that define the
 * actions a user with that role is allowed to perform. This enum facilitates
 * role-based access control within the application.
 * <p>
 * TODO: Create a DB table for roles instead of using an enum
 */
@RequiredArgsConstructor
public enum Role {

    /**
     * Role representing a standard user with basic privileges.
     * This role does not have any special permissions.
     */
    USER(Collections.emptySet()),

    /**
     * Role representing an administrator with extended privileges.
     * This role includes a comprehensive set of permissions for both admin and
     * manager-level operations.
     */
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),

    /**
     * Role representing a manager with intermediate privileges.
     * This role includes permissions for various management-level operations.
     */
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    /**
     * The set of permissions associated with the role.
     */
    @Getter
    private final Set<Permission> permissions;

    /**
     * Gets the list of authorities granted to the role.
     *
     * @return A list of {@link SimpleGrantedAuthority} objects representing the
     *         permissions granted to the role.
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}