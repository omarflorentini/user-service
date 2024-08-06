package com.example.user.service;

import com.example.user.dto.LoginRequest;
import com.example.user.dto.LoginResponse;
import com.example.user.dto.SignupRequest;
import com.example.user.dto.SignupResponse;

public interface UserService {

    SignupResponse signup(SignupRequest signupRequest);

    LoginResponse login(LoginRequest loginRequest);

}
