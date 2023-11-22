package com.shogiusa.shogiapi.auditing;

import com.shogiusa.shogiapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationAuditAwareTest {

    private ApplicationAuditAware applicationAuditAware;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationAuditAware = new ApplicationAuditAware();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void shouldReturnUserIdWhenAuthenticated() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Optional<Long> currentAuditor = applicationAuditAware.getCurrentAuditor();

        // Assert
        assertTrue(currentAuditor.isPresent());
        assertEquals(1L, currentAuditor.get());

        // Clean up
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldReturnEmptyWhenNotAuthenticated() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        Optional<Long> currentAuditor = applicationAuditAware.getCurrentAuditor();

        // Assert
        assertFalse(currentAuditor.isPresent());
    }

    @Test
    public void shouldReturnEmptyForAnonymousUser() {
        // Arrange
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        when(securityContext.getAuthentication()).thenReturn(new AnonymousAuthenticationToken("key", "anonymousUser", authorities));

        // Act
        Optional<Long> currentAuditor = applicationAuditAware.getCurrentAuditor();

        // Assert
        assertFalse(currentAuditor.isPresent());
    }

    // Additional tests can be written for other edge cases, if any.
}