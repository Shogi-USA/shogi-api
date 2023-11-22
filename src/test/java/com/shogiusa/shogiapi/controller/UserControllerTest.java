package com.shogiusa.shogiapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shogiusa.shogiapi.response.UserInfoResponse;
import com.shogiusa.shogiapi.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.security.Principal;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserServiceImpl userService;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testGetCurrentUserInfo() throws Exception {
        String testEmail = "test@example.com";
        UserInfoResponse mockResponse = new UserInfoResponse();
        mockResponse.setEmail(testEmail); // Make sure to set the email field

        when(principal.getName()).thenReturn(testEmail);
        when(userService.getUserInfoByEmail(testEmail)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/users/info")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testEmail));
    }
}
