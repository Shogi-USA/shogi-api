package com.shogiusa.shogiapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response returned after a successful authentication process.
 * <p>
 * This class encapsulates the authentication response details, primarily the access token
 * provided to a user upon successful authentication. The access token is used for subsequent
 * authenticated requests to the system.
 * <p>
 * The {@link Data} annotation from Lombok generates boilerplate code such as getters,
 * setters, equals, hashCode, and toString methods. {@link Builder}, {@link AllArgsConstructor},
 * and {@link NoArgsConstructor} annotations are used for convenient object creation and instantiation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The JWT access token issued to the user upon successful authentication.
     * <p>
     * This token is used to authenticate subsequent requests made by the user. The token should be
     * included in the Authorization header of HTTP requests. {@link JsonProperty} annotation specifies
     * the serialized name of this field as 'access_token'.
     */
    @JsonProperty("access_token")
    private String accessToken;
}