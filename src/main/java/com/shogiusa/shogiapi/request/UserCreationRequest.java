package com.shogiusa.shogiapi.request;

import com.shogiusa.shogiapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for creating a new user in the system.
 * <p>
 * This class encapsulates all the necessary information required to create a new user account. It includes
 * personal details, account credentials, and other related attributes.
 * <p>
 * The {@link Data} annotation from Lombok simplifies the creation of boilerplate code such as getters,
 * setters, equals, hashCode, and toString methods. {@link Builder}, {@link AllArgsConstructor}, and
 * {@link NoArgsConstructor} annotations are used to facilitate object creation and instantiation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {

    /**
     * The chosen username for the new user account.
     * <p>
     * This username is used as a unique identifier for the user within the system.
     */
    private String username;

    /**
     * The email address associated with the new user account.
     * <p>
     * This email is used for communication and potentially for login purposes.
     */
    private String email;

    /**
     * The first name of the user.
     * <p>
     * This field represents the user's given name.
     */
    private String firstName;

    /**
     * The last name of the user.
     * <p>
     * This field represents the user's family name or surname.
     */
    private String lastName;

    /**
     * The display name for the user.
     * <p>
     * This name is used as a public-facing identifier for the user within the system.
     */
    private String displayName;

    /**
     * The password for the user's account.
     * <p>
     * This password, along with the username or email, is used for authentication purposes.
     */
    private String password;

    /**
     * The category ID to which the user belongs.
     * <p>
     * This ID references a specific category within the system, such as 'Adult' or 'Child'.
     */
    private Long categoryId;

    /**
     * The branch ID associated with the user.
     * <p>
     * This ID references a specific branch or location within the organization, such as 'LA', 'SF', etc.
     */
    private Long clubBranchId;

    /**
     * The level ID indicating the user's skill or rank.
     * <p>
     * This ID corresponds to a specific skill level or rank, like '1 kyu', '1 dan', etc.
     */
    private Long levelId;

    /**
     * The role assigned to the user.
     * <p>
     * This role determines the user's permissions and access within the system.
     */
//    private Role role;
}
