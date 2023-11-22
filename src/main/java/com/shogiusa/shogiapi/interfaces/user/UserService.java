package com.shogiusa.shogiapi.interfaces.user;

import com.shogiusa.shogiapi.model.User;
import com.shogiusa.shogiapi.request.UserCreationRequest;
import com.shogiusa.shogiapi.response.UserCreationFormData;
import com.shogiusa.shogiapi.response.UserInfoResponse;

public interface UserService {

    User updateUser(UserCreationRequest request);

    UserInfoResponse getUserInfoByEmail(String email);

    UserCreationFormData getUserCreationFormData();
}