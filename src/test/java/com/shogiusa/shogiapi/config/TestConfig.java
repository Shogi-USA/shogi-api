package com.shogiusa.shogiapi.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.repository.TokenRepository;

public class TestConfig {

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    @Primary
    public TokenRepository tokenRepository() {
        return Mockito.mock(TokenRepository.class);
    }
}
