package com.shogiusa.shogiapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.AuthenticationResponse;
import com.shogiusa.shogiapi.service.user.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthenticationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthenticationServiceImpl service;

    @InjectMocks
    private AuthenticationController controller;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testRegister_Success() throws Exception {
        UserCreationRequest request = new UserCreationRequest();
        // Set properties for request
        AuthenticationResponse mockResponse = new AuthenticationResponse();
        // Set properties for mockResponse, including the accessToken
        String expectedAccessToken = "someAccessTokenValue";
        mockResponse.setAccessToken(expectedAccessToken);

        when(service.register(any(UserCreationRequest.class), any(HttpServletResponse.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(expectedAccessToken));
    }
}

