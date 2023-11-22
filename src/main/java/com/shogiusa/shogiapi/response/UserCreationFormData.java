package com.shogiusa.shogiapi.response;

import com.shogiusa.shogiapi.model.ClubBranch;
import com.shogiusa.shogiapi.model.AgeCategory;
import com.shogiusa.shogiapi.model.PlayerShogilLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Encapsulates the data required for rendering a user creation form in the frontend.
 * <p>
 * This class provides structured information about the different categories, branches, levels,
 * and other fields that are part of the user creation process. It is used to dynamically
 * generate the user creation form with appropriate fields and options.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationFormData {
    /**
     * Array of {@link AgeCategory} objects representing the different categories a user can belong to.
     */
    private AgeCategory[] categories;

    /**
     * Array of {@link ClubBranch} objects representing the different branches or locations available.
     */
    private ClubBranch[] branches;

    /**
     * Array of {@link PlayerShogilLevel} objects representing the different skill or experience levels.
     */
    private PlayerShogilLevel[] playerShogilLevels;

    /**
     * Array of {@link UserCreationField} objects representing additional fields required in the user creation form.
     */
    private UserCreationField[] fields;
}
