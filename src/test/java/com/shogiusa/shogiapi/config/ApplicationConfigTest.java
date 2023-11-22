package com.shogiusa.shogiapi.config;

import com.shogiusa.shogiapi.auditing.ApplicationAuditAware;
import com.shogiusa.shogiapi.service.user.LogoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ApplicationConfig.class, TestConfig.class})
public class ApplicationConfigTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthenticationConfiguration authenticationConfiguration;

    @Test
    public void contextLoads() {
        assertNotNull(context, "Context should not be null");
    }

    @Test
    public void userDetailsServiceBean() {
        UserDetailsService userDetailsService = context.getBean(UserDetailsService.class);
        assertNotNull(userDetailsService, "UserDetailsService should not be null");
    }

    @Test
    public void authenticationProviderBean() {
        AuthenticationProvider authenticationProvider = context.getBean(AuthenticationProvider.class);
        assertNotNull(authenticationProvider, "AuthenticationProvider should not be null");
    }

    @Test
    public void auditorAwareBean() {
        ApplicationAuditAware auditorAware = context.getBean(ApplicationAuditAware.class);
        assertNotNull(auditorAware, "ApplicationAuditAware should not be null");
    }

    @Test
    public void authenticationManagerBean() {
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        assertNotNull(authenticationManager, "AuthenticationManager should not be null");
    }

    @Test
    public void passwordEncoderBean() {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        assertNotNull(passwordEncoder, "PasswordEncoder should not be null");
    }

    @Test
    public void logoutHandlerBean() {
        LogoutHandler logoutHandler = context.getBean(LogoutHandler.class);
        assertTrue(logoutHandler instanceof LogoutServiceImpl, "LogoutHandler should be an instance of LogoutService");
    }
}