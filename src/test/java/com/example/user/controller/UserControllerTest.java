package com.example.user.controller;

import com.example.user.dto.SignupRequest;
import com.example.user.service.UserService;
import com.example.user.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Signup OK")
    public void signupOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(
                                                           TestUtil.createValidSignupRequest())))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Signup throw email es requerido")
    public void signupThrowEmailRequired() throws Exception {
        var signupRequest = SignupRequest.builder()
                                         .password(TestUtil.VALID_PASS)
                                         .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Signup throw email y password son inválidos")
    public void signupThrowEmailAndPasswordAreInvalid() throws Exception {
        var signupRequest = SignupRequest.builder()
                                         .email(TestUtil.INVALID_EMAIL)
                                         .password(TestUtil.INVALID_PASS)
                                         .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Signup throw password es requerido")
    public void signupThrowPasswordRequired() throws Exception {
        var signupRequest = SignupRequest.builder()
                                         .email(TestUtil.VALID_EMAIL)
                                         .build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Login OK")
    public void loginOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .accept(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(
                                                           TestUtil.createValidSignupRequest())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }

}