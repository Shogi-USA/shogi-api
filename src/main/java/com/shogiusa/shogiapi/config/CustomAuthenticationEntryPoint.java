package com.shogiusa.shogiapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Custom implementation of {@link AuthenticationEntryPoint} used to commence an authentication scheme.
 * <p>
 * This entry point is invoked when an authentication exception is thrown due to an unauthenticated user trying to
 * access a resource that requires authentication. It sends a 401 Unauthorized response along with an error message.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences an authentication scheme.
     * <p>
     * This method is called whenever an exception is thrown due to an unauthenticated user trying to access
     * a resource that requires authentication.
     *
     * @param request       that resulted in an {@link AuthenticationException}
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException in case of an I/O error
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // You can add more specific checks and responses here if needed
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
    }
}