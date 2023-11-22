package com.shogiusa.shogiapi.controller;

import com.shogiusa.shogiapi.exception.UserAlreadyExistsException;
import com.shogiusa.shogiapi.request.AuthenticationRequest;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.AuthenticationResponse;
import com.shogiusa.shogiapi.service.user.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

/**
 * REST controller for handling authentication-related operations.
 * <p>
 * This controller provides endpoints for user registration, authentication, and token refresh.
 * It uses the {@link AuthenticationServiceImpl} to perform the underlying logic.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // Service that encapsulates the authentication logic
    private final AuthenticationServiceImpl service;

    /**
     * Registers a new user in the system.
     * <p>
     * This method accepts a {@link UserCreationRequest} and returns an {@link AuthenticationResponse}
     * containing an access token if the registration is successful.
     *
     * @param request  The user creation request containing user details
     * @param response The HttpServletResponse
     * @return ResponseEntity containing an AuthenticationResponse if successful
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserCreationRequest request, HttpServletResponse response) {
        LOGGER.info("Registering new user with email {}", request.getEmail());
        return ResponseEntity.ok(service.register(request, response));
    }

    /**
     * Handles the UserAlreadyExistsException.
     * <p>
     * This method is invoked when an attempt to register a user with an existing username or email is made.
     *
     * @param ex The exception thrown when a user already exists
     * @return ResponseEntity with the error message and a CONFLICT (409) status
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    /**
     * Authenticates a user and provides an access token.
     * <p>
     * This method accepts an {@link AuthenticationRequest} and returns an {@link AuthenticationResponse}
     * containing an access token if the authentication is successful.
     *
     * @param request  The authentication request containing login credentials
     * @param response The HttpServletResponse
     * @return ResponseEntity containing an AuthenticationResponse if successful
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response) {
        LOGGER.info("Authenticating user with email {}", request.getEmail());
        return ResponseEntity.ok(service.authenticate(request, response));
    }

    /**
     * Refreshes the authentication token.
     * <p>
     * This method is used to issue a new access token using a valid refresh token.
     *
     * @param request  The HttpServletRequest
     * @param response The HttpServletResponse
     * @throws IOException If an input or output exception occurs
     */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request, HttpServletResponse response ) throws IOException {
        service.refreshToken(request, response);
    }
}