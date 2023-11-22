package com.shogiusa.shogiapi.service.user;

import com.shogiusa.shogiapi.exception.UserAlreadyExistsException;
import com.shogiusa.shogiapi.model.User;
import com.shogiusa.shogiapi.repository.TokenRepository;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.request.AuthenticationRequest;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.AuthenticationResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private UserCreationRequest userCreationRequest;

    private AuthenticationRequest authenticationRequest;

    private User user;

    @BeforeEach
    void setUp() {
        userCreationRequest = UserCreationRequest.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password")
                .email("test@example.com")
                .build();

    }

    @Test
    void whenRegisterUser_thenSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        authenticationService.register(userCreationRequest, httpServletResponse);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenRegisterUserWithExistingEmail_thenThrowException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(userCreationRequest, httpServletResponse);
        });
    }

    @Test
    void whenRegisterUserWithExistingUsername_thenThrowException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(userCreationRequest, httpServletResponse);
        });
    }

    // Additional tests for authenticate, refreshToken, etc.
    @Test
    void whenAuthenticateUser_thenGenerateTokens() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("user@example.com", "password");
        User mockUser = new User();
        mockUser.setEmail(request.getEmail());

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(UserDetails.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request, httpServletResponse);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        // Verify that the tokens are set as cookies in the response
        verify(httpServletResponse).addCookie(any(Cookie.class));
    }
}
