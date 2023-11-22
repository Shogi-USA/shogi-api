package com.shogiusa.shogiapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for user authentication.
 * <p>
 * This class is used to encapsulate the data required for a user authentication process,
 * typically including an email address and a password. It's commonly used when users attempt
 * to log in to the system.
 * <p>
 * The {@link Data} annotation from Lombok automatically generates boilerplate code such as
 * getters, setters, equals, hashCode, and toString methods. {@link Builder}, {@link AllArgsConstructor},
 * and {@link NoArgsConstructor} annotations facilitate the creation and instantiation of
 * {@code AuthenticationRequest} objects.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The email address of the user attempting to log in.
     * <p>
     * This is typically used as the primary credential identifier in the authentication process.
     */
    private String email;

    /**
     * The password associated with the user's account.
     * <p>
     * This field represents the secret key or code that, in conjunction with the email address,
     * is used to authenticate the user's identity.
     */
    private String password;
}