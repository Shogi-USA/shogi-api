package com.shogiusa.shogiapi.service.user;

import com.shogiusa.shogiapi.interfaces.user.LogoutService;
import com.shogiusa.shogiapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Service to handle logout functionality in the application.
 */
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenRepository tokenRepository;

    /**
     * Performs the logout operation by revoking the JWT token and clearing the security context.
     *
     * @param request The HTTP request containing the JWT token.
     * @param response The HTTP response.
     * @param authentication The current authentication object.
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            // Marks the token as expired and revoked in the database
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            // Clears the security context to ensure the user is logged out
            SecurityContextHolder.clearContext();
        }
    }
}