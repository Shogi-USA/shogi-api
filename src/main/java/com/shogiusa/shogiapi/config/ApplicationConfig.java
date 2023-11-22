package com.shogiusa.shogiapi.config;

import com.shogiusa.shogiapi.auditing.ApplicationAuditAware;
import com.shogiusa.shogiapi.repository.TokenRepository;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.service.user.LogoutServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Configures various components required for Spring Security in the application.
 * This includes user details service, authentication provider, password encoder, and logout handler.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    private final UserRepository repository;

    /**
     * Defines the UserDetailsService bean that loads user-specific data.
     * It is used by the DaoAuthenticationProvider to load details about the user during authentication.
     *
     * @return A UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Defines the AuthenticationProvider bean used for checking user credentials and roles.
     *
     * @return An AuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * AuditorAware bean for auditing purposes in Spring Data JPA.
     *
     * @return An AuditorAware implementation.
     */
    @Bean
    public AuditorAware<Long> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * AuthenticationManager bean used to authenticate a given request in Spring Security.
     *
     * @param config AuthenticationConfiguration instance.
     * @return An AuthenticationManager instance.
     * @throws Exception if an error occurs while creating the bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /**
     * PasswordEncoder bean for encrypting and decrypting passwords in the application.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * LogoutHandler bean for handling logout logic.
     *
     * @param tokenRepository Repository for handling tokens.
     * @return A LogoutHandler instance.
     */
    @Bean
    public LogoutHandler logoutHandler(TokenRepository tokenRepository) {
        return new LogoutServiceImpl(tokenRepository);
    }
}