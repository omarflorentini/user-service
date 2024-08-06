package com.example.user.controller;

import com.example.user.dto.LoginRequest;
import com.example.user.dto.LoginResponse;
import com.example.user.dto.SignupRequest;
import com.example.user.dto.SignupResponse;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(
            @Valid
            @RequestBody
            SignupRequest signupRequest) {
        return this.userService.signup(signupRequest);
    }

    @PostMapping("login")
    public LoginResponse login(
            @Valid
            @RequestBody
            LoginRequest loginRequest) {
        return this.userService.login(loginRequest);
    }

}
