package com.shogiusa.shogiapi.controller;

import com.shogiusa.shogiapi.exception.ServiceNotImplementedException;
import com.shogiusa.shogiapi.model.User;
import com.shogiusa.shogiapi.request.UserUpdateRequest;
import com.shogiusa.shogiapi.response.UserCreationFormData;
import com.shogiusa.shogiapi.response.UserInfoResponse;
import com.shogiusa.shogiapi.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/**
 * REST controller for user-related operations.
 * <p>
 * This controller provides endpoints for user management including updating, deleting,
 * and retrieving user information.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    /**
     * Updates an existing user.
     * <p>
     * This method accepts a {@link UserUpdateRequest} containing updated user details.
     *
     * @param request The user update request containing new user details
     * @return ResponseEntity containing the updated User
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        LOGGER.info("Updating user with ID {}", id);
        // TODO: implement the feature
        throw new ServiceNotImplementedException("Update user feature not implemented");
    }

    /**
     * Deletes a user.
     * <p>
     * This endpoint should only be accessible to administrators. It deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     * @return ResponseEntity containing the details of the deleted User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        LOGGER.info("Deleting user with ID {}", id);
        // TODO: implement the feature
        throw new ServiceNotImplementedException("Update user feature not implemented");
    }


    /**
     * Retrieves user creation form data.
     * <p>
     * This endpoint provides necessary data to populate user creation forms, such as role lists, etc.
     *
     * @return ResponseEntity containing UserCreationFormData
     */
    @GetMapping("/creation-form-data")
    public ResponseEntity<UserCreationFormData> getUserCreationFormData() {
        LOGGER.info("Retrieving user creation form data");
        // TODO: this can be utilized instead of populating everything in the frontend
        return ResponseEntity.ok(userService.getUserCreationFormData());
    }

    /**
     * Retrieves information about the currently authenticated user.
     * <p>
     * This endpoint uses the {@link Principal} to identify the current user and returns their information.
     *
     * @param principal The security principal of the requesting user
     * @return ResponseEntity containing UserInfoResponse with current user's information
     */
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getCurrentUserInfo(Principal principal) {
        LOGGER.info("Retrieving info for current user");
        String email = principal.getName();
        UserInfoResponse currentUser = userService.getUserInfoByEmail(email);
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Handles the ServiceNotImplementedException.
     *
     * This method is invoked when a feature is not implemented.
     *
     * @param ex The exception thrown when a feature is not implemented
     * @return ResponseEntity with the error message and a NOT_IMPLEMENTED (501) status
     */
    @ExceptionHandler(ServiceNotImplementedException.class)
    public ResponseEntity<Object> handleServiceNotImplemented(ServiceNotImplementedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ex.getMessage());
    }
}