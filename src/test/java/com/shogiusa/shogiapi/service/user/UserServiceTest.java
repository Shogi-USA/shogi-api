package com.shogiusa.shogiapi.service.user;

import static org.mockito.Mockito.*;
import com.shogiusa.shogiapi.exception.UserNotFoundException;
import com.shogiusa.shogiapi.model.User;
import com.shogiusa.shogiapi.repository.UserRepository;
import com.shogiusa.shogiapi.response.UserInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserInfoByEmail_Success() {
        String testEmail = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(testEmail);
        // Set other properties of mockUser as needed

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(mockUser));

        UserInfoResponse response = userService.getUserInfoByEmail(testEmail);

        assertEquals(testEmail, response.getEmail());
        // Add other assertions as needed
    }

    @Test
    public void testGetUserInfoByEmail_UserNotFound() {
        String testEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserInfoByEmail(testEmail));
    }
}
