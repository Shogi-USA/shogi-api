package com.shogiusa.shogiapi.response;

import com.shogiusa.shogiapi.enums.Role;
import com.shogiusa.shogiapi.model.ClubBranch;
import com.shogiusa.shogiapi.model.AgeCategory;
import com.shogiusa.shogiapi.model.PlayerShogilLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response object containing detailed information about a user.
 * <p>
 * This class is used to encapsulate and transfer user data, particularly in response to
 * information requests about a specific user. It includes personal details as well as
 * associated category, branch, level, and role within the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    /**
     * The username of the user. This is typically a unique identifier.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The display name of the user. This can be different from the actual first and last name.
     */
    private String displayName;

    /**
     * The category to which the user belongs. This could represent a classification like 'Adult' or 'Child'.
     */
    private AgeCategory ageCategory;

    /**
     * The branch or location with which the user is associated. This could be a city or regional branch.
     */
    private ClubBranch branch;

    /**
     * The level of the user, indicating their proficiency or rank, such as '1 kyu', '1 dan', etc.
     */
    private PlayerShogilLevel playerShogilLevel;

    /**
     * The role of the user within the system, such as 'USER', 'ADMIN', or 'MANAGER'.
     */
    private Role role;
}
