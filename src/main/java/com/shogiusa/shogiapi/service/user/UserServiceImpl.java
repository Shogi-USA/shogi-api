package com.shogiusa.shogiapi.service.user;

import com.shogiusa.shogiapi.exception.UserNotFoundException;
import com.shogiusa.shogiapi.interfaces.user.UserService;
import com.shogiusa.shogiapi.model.ClubBranch;
import com.shogiusa.shogiapi.model.AgeCategory;
import com.shogiusa.shogiapi.model.PlayerShogilLevel;
import com.shogiusa.shogiapi.model.User;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.UserCreationFormData;
import com.shogiusa.shogiapi.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user-related operations such as updating user information,
 * retrieving user details, and generating user creation form data.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private  UserRepository userRepository;

    /**
     * Updates the user information based on the provided request.
     *
     * @param request The user creation request containing updated user details.
     * @return The updated User object.
     */
    @Override
    public User updateUser(UserCreationRequest request) {
        // TODO: implement the feature
        return new User();
    }

    /**
     * Retrieves user information based on the provided email.
     *
     * @param email The email of the user to retrieve information for.
     * @return UserInfoResponse containing the user's information.
     */
    @Override
    public UserInfoResponse getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return convertToUserInfoResponse(user);
    }

    /**
     * Retrieves user information based on the provided username.
     *
     * @param user The user to retrieve information for.
     * @return UserInfoResponse containing the user's information.
     */
    private UserInfoResponse convertToUserInfoResponse(User user) {
        return UserInfoResponse.builder()
                .username(user.getActualUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .ageCategory(user.getAgeCategory())
                .branch(user.getClubBranch())
                .playerShogilLevel(user.getPlayerShogilLevel())
                .role(user.getRole())
                .build();
    }

    /**
     * Generates and returns user creation form data including categories, branches, and levels.
     * This data can be used to populate form fields for user creation in the frontend.
     *
     * @return UserCreationFormData containing information for user creation form.
     */
    @Override
    public UserCreationFormData getUserCreationFormData() {
        UserCreationFormData userCreationFormData = new UserCreationFormData();
        AgeCategory ageCategory1 = new AgeCategory();
        ageCategory1.setName("Adult");
        AgeCategory ageCategory2 = new AgeCategory();
        ageCategory1.setName("Child");
        AgeCategory[] categories = {ageCategory1, ageCategory2};
        userCreationFormData.setCategories(categories);

        ClubBranch branch1 = new ClubBranch();
        branch1.setName("San Fransisco");
        ClubBranch branch2 = new ClubBranch();
        branch2.setName("Los Angeles");
        ClubBranch branch3 = new ClubBranch();
        branch3.setName("New York");
        ClubBranch[] branches = {branch1, branch2, branch3};
        userCreationFormData.setBranches(branches);

        PlayerShogilLevel level1 = new PlayerShogilLevel();
        level1.setName("15 kyu");
        PlayerShogilLevel level2 = new PlayerShogilLevel();
        level2.setName("14 kyu");
        PlayerShogilLevel level3 = new PlayerShogilLevel();
        level3.setName("13 kyu");
        PlayerShogilLevel level4 = new PlayerShogilLevel();
        level4.setName("12 kyu");
        PlayerShogilLevel level5 = new PlayerShogilLevel();
        level5.setName("11 kyu");
        PlayerShogilLevel level6 = new PlayerShogilLevel();
        level6.setName("10 kyu");
        PlayerShogilLevel level7 = new PlayerShogilLevel();
        level7.setName("9 kyu");
        PlayerShogilLevel level8 = new PlayerShogilLevel();
        level8.setName("8 kyu");
        PlayerShogilLevel level9 = new PlayerShogilLevel();
        level9.setName("7 kyu");
        PlayerShogilLevel level10 = new PlayerShogilLevel();
        level10.setName("6 kyu");
        PlayerShogilLevel level11 = new PlayerShogilLevel();
        level11.setName("5 kyu");
        PlayerShogilLevel level12 = new PlayerShogilLevel();
        level12.setName("4 kyu");
        PlayerShogilLevel level13 = new PlayerShogilLevel();
        level13.setName("3 kyu");
        PlayerShogilLevel level14 = new PlayerShogilLevel();
        level14.setName("2 kyu");
        PlayerShogilLevel level15 = new PlayerShogilLevel();
        level15.setName("1 kyu");
        PlayerShogilLevel level16 = new PlayerShogilLevel();
        level16.setName("1 dan");
        PlayerShogilLevel level17 = new PlayerShogilLevel();
        level17.setName("2 dan");
        PlayerShogilLevel level18 = new PlayerShogilLevel();
        level18.setName("3 dan");
        PlayerShogilLevel level19 = new PlayerShogilLevel();
        level19.setName("4 dan");
        PlayerShogilLevel level20 = new PlayerShogilLevel();
        level20.setName("5 dan");
        PlayerShogilLevel level21 = new PlayerShogilLevel();
        level21.setName("6 dan");
        PlayerShogilLevel level22 = new PlayerShogilLevel();
        level22.setName("7 dan");
        PlayerShogilLevel level23 = new PlayerShogilLevel();
        level23.setName("8 dan");
        PlayerShogilLevel level24 = new PlayerShogilLevel();
        level24.setName("9 dan");
        PlayerShogilLevel[] levels = {level1, level2, level3, level4, level5, level6, level7, level8, level9, level10,
                level11, level12, level13, level14, level15, level16, level17, level18, level19, level20,
                level21, level22, level23, level24};
        userCreationFormData.setPlayerShogilLevels(levels);

        return userCreationFormData;
    }
}
