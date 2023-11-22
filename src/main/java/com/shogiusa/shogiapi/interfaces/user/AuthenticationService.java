package com.shogiusa.shogiapi.interfaces.user;

import com.shogiusa.shogiapi.request.AuthenticationRequest;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(UserCreationRequest request, HttpServletResponse response);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}