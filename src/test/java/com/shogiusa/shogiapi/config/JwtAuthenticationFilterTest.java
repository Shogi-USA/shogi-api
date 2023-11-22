package com.shogiusa.shogiapi.config;

import com.shogiusa.shogiapi.enums.TokenType;
import com.shogiusa.shogiapi.model.Token;
import com.shogiusa.shogiapi.repository.TokenRepository;
import com.shogiusa.shogiapi.service.user.JwtServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenRepository);
    }

    @Test
    public void testFilterWithValidJwt() throws ServletException, IOException {
        String testUsername = "user@example.com";
        String testToken = "Bearer validToken";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", testToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        when(jwtService.extractUsername("validToken")).thenReturn(testUsername);
        when(userDetailsService.loadUserByUsername(testUsername)).thenReturn(new User(testUsername, "password", new ArrayList<>()));
        when(tokenRepository.findByToken("validToken"))
                .thenReturn(java.util.Optional.of(
                        Token.builder()
                                .token("validToken")
                                .tokenType(TokenType.BEARER) // assuming TokenType.BEARER is the correct type
                                .revoked(false)
                                .expired(false)
                                // set 'user' field if necessary
                                .build()
                ));
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        // Additional assertions can be made here, such as verifying the SecurityContext
    }

    @Test
    public void testFilterWithExpiredJwt() throws ServletException, IOException {
        String testToken = "Bearer expiredToken";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", testToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        when(jwtService.extractUsername("expiredToken")).thenThrow(new ExpiredJwtException(null, null, "Token expired"));

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain, never()).doFilter(any(), any()); // Verify doFilter is not called
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus()); // Verify the correct error response
    }

    @Test
    public void testFilterWithInvalidJwt() throws ServletException, IOException {
        String testToken = "Bearer invalidToken";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", testToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        when(jwtService.extractUsername("invalidToken")).thenReturn(null); // Simulate invalid token

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        // Verify that no authentication is set in SecurityContext
    }

    @Test
    public void testFilterWithNoJwt() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        // Verify that no authentication is set in SecurityContext
    }

    @Test
    public void testBypassAuthenticationForSpecificUrls() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/auth");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        // No need to check SecurityContext as the filter should bypass this path
    }
}
