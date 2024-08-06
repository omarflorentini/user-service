package com.example.user.util;

import com.example.user.dto.LoginRequest;
import com.example.user.dto.PhoneDTO;
import com.example.user.dto.SignupRequest;
import com.example.user.dto.SignupResponse;
import com.example.user.model.Phone;
import com.example.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class TestUtil {

    public static final UUID VALID_UUID = UUID.randomUUID();
    public static final String VALID_EMAIL = "user@domain.com";
    public static final String VALID_PASS = "qAsnm1w2";
    public static final String VALID_TOKEN = "";

    public static final String INVALID_EMAIL = "user_domain.com";
    public static final String INVALID_PASS = "Ae12nmwewbg7";

    public static SignupRequest createValidSignupRequest() {
        return SignupRequest.builder()
                            .email(VALID_EMAIL)
                            .password(VALID_PASS)
                            .phones(Collections.singletonList(PhoneDTO.builder()
                                                                      .number(119998877L)
                                                                      .cityCode(1900)
                                                                      .countryCode("0054")
                                                                      .build()))
                            .build();
    }

    public static User createValidUser() {
        return User.builder()
                   .id(VALID_UUID)
                   .email(VALID_EMAIL)
                   .password(VALID_PASS)
                   .created(LocalDateTime.now())
                   .lastLogin(LocalDateTime.now())
                   .token(VALID_TOKEN)
                   .active(Boolean.TRUE)
                   .build();
    }

    public static LoginRequest createValidLoginRequest() {
        return LoginRequest.builder()
                           .email(VALID_EMAIL)
                           .password(VALID_PASS)
                           .build();
    }

    public static User createInactiveUser() {
        return User.builder()
                   .id(VALID_UUID)
                   .email(VALID_EMAIL)
                   .password(VALID_PASS)
                   .created(LocalDateTime.now())
                   .lastLogin(LocalDateTime.now())
                   .token(VALID_TOKEN)
                   .active(Boolean.FALSE)
                   .build();
    }

    public static User createValidUserWithOnePhone() {
        return User.builder()
                   .id(VALID_UUID)
                   .email(VALID_EMAIL)
                   .password(VALID_PASS)
                   .created(LocalDateTime.now())
                   .lastLogin(LocalDateTime.now())
                   .token(VALID_TOKEN)
                   .active(Boolean.TRUE)
                   .phones(Collections.singletonList(
                           Phone.builder()
                                .number(119998877L)
                                .cityCode(1900)
                                .countryCode("0054")
                                .build()))
                   .build();
    }

    public static SignupRequest createInValidEmailSignupRequest() {
        return SignupRequest.builder()
                            //.email(INVALID_EMAIL)
                            .password(VALID_PASS)
                            .build();
    }

}
