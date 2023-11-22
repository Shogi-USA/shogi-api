package com.shogiusa.shogiapi.service.user;

import com.shogiusa.shogiapi.model.Token;
import com.shogiusa.shogiapi.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutServiceImpl logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenLogout_thenTokenIsRevoked() {
        // Arrange
        String jwt = "jwtToken";
        Token token = new Token();
        token.setToken(jwt);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(token));

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        verify(tokenRepository).findByToken(jwt);
        verify(tokenRepository).save(token);
        assertTrue(token.isRevoked());
        assertTrue(token.isExpired());
    }

    @Test
    void whenLogoutWithNoAuthorizationHeader_thenDoNothing() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void whenLogoutWithInvalidAuthorizationHeader_thenDoNothing() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Invalid header");

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
    }
}