package com.shogiusa.shogiapi.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final String SECRET_KEY = "your-256-bit-secret";

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl();

        // Use ReflectionTestUtils to set the private fields
        ReflectionTestUtils.setField(jwtService, "secretKey",
                "your256bitsecretkeyforjwtrandomstringstringstringstring");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L); // 1 hour
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 7200000L); // 2 hours
    }

    @Test
    void whenGenerateToken_thenSucceeds() {
        // Arrange
        String username = "user";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void whenValidateToken_thenTokenIsValid() {
        // Arrange
        String username = "user";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .authorities(Collections.emptyList())
                .build();
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void whenTokenExpired_thenTokenIsNotValid() {
        // Arrange
        String username = "user";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Set the expiration to the past to simulate an expired token
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -3600000L); // token expired an hour ago
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void whenGenerateRefreshToken_thenSucceeds() {
        // Arrange
        String username = "user";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Assert
        assertNotNull(refreshToken);
        // Refresh token typically does not contain authorities or other credentials
    }

    // Add more tests for other methods like extractClaim, extractExpiration, etc.
}