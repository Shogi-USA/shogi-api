package com.shogiusa.shogiapi.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shogiusa.shogiapi.enums.Role;
import com.shogiusa.shogiapi.enums.TokenType;
import com.shogiusa.shogiapi.exception.UserAlreadyExistsException;
import com.shogiusa.shogiapi.interfaces.user.AuthenticationService;
import com.shogiusa.shogiapi.model.Token;
import com.shogiusa.shogiapi.repository.TokenRepository;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.request.AuthenticationRequest;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.AuthenticationResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shogiusa.shogiapi.model.User;
import java.io.IOException;

/**
 * Service class for handling authentication and registration processes.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    /**
     * Registers a new user and generates authentication tokens.
     *
     * @param request User creation request data.
     * @param response HttpServletResponse to set cookies.
     * @return AuthenticationResponse with access token.
     */
    @Override
    public AuthenticationResponse register(UserCreationRequest request, HttpServletResponse response) {
        // check if user exists, if so, throw exception
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("The email is already taken");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("The username is already taken");
        }

        var user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .displayName(request.getDisplayName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

        // Generate tokens
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        // Set the refresh token as an HTTP-only cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        // Set other cookie attributes as necessary, like `Secure`, `MaxAge`, etc.
        // If your application runs over HTTPS, it's a good practice to set the `Secure` flag.
        response.addCookie(refreshCookie);

        // Return the response with only the access token
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    /**
     * Authenticates a user and generates new authentication tokens.
     *
     * @param request Authentication request data.
     * @param response HttpServletResponse to set cookies.
     * @return AuthenticationResponse with access token.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        // Revoke existing tokens and save the new token
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        // Set the refresh token as an HTTP-only cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        // Add 'Secure' flag if the application runs over HTTPS
        // refreshCookie.setSecure(true);
        response.addCookie(refreshCookie);

        // Return the response with only the access token
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    /**
     * Saves user token in the repository.
     *
     * @param user User to associate with the token.
     * @param jwtToken JWT token to be saved.
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Revokes all valid tokens of a user.
     *
     * @param user User whose tokens are to be revoked.
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Refreshes the authentication token for a user based on the refresh token provided in cookies.
     *
     * @param request HttpServletRequest to retrieve the refresh token.
     * @param response HttpServletResponse to send back the new access token.
     * @throws IOException If an input or output exception occurs.
     */
    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the refresh token from the cookie
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is missing");
            return;
        }

        // Extract user email from the refresh token
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                // Optionally generate a new refresh token
                // var newRefreshToken = jwtService.generateRefreshToken(user);
                // Set the new refresh token as an HTTP-only cookie if needed

                // Build response with the new access token
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        // .refreshToken(newRefreshToken) // include if a new refresh token is generated
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}