package com.example.user.service;

import com.example.user.exception.UserException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static com.example.user.exception.ExceptionMessage.USER_EXISTS;
import static com.example.user.exception.ExceptionMessage.USER_NOT_ACTIVE;
import static com.example.user.exception.ExceptionMessage.USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Signup throw user exists")
    void signupThrowUserException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

        var exception = assertThrows(UserException.class,
                                     () -> userService.signup(TestUtil.createValidSignupRequest()));

        assertNotNull(exception.getErrorDTO());
        assertEquals(USER_EXISTS.getDetail(), exception.getMessage());
        verify(userRepository).existsByEmail(anyString());
    }

    @Test
    @DisplayName("Signup throw DatabaseException")
    void signupThrowDatabaseException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
        when(userRepository.save(any(User.class))).thenThrow(new DataAccessException("error") {
        });

        var exception = assertThrows(DataAccessException.class,
                                     () -> userService.signup(TestUtil.createValidSignupRequest()));

        assertNotNull(exception);
        assertEquals("error", exception.getMessage());
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Signup OK")
    void signupOk() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
        when(userRepository.save(any(User.class))).thenReturn(TestUtil.createValidUser());

        var response = userService.signup(TestUtil.createValidSignupRequest());

        assertEquals(TestUtil.VALID_UUID, response.getId());
        assertEquals(Boolean.TRUE, response.getIsActive());

        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Login throw user not exists")
    void loginThrowUserNotExists() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenThrow(
                new UserException(USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD));

        var exception = assertThrows(UserException.class,
                                     () -> userService.login(TestUtil.createValidLoginRequest()));

        assertNotNull(exception.getErrorDTO());
        assertEquals(USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD.getDetail(), exception.getMessage());
        verify(userRepository).findByEmailAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Login throw user not active")
    void loginThrowUserNotActive() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(
                Optional.of(TestUtil.createInactiveUser()));

        var exception = assertThrows(UserException.class,
                                     () -> userService.login(TestUtil.createValidLoginRequest()));

        assertNotNull(exception.getErrorDTO());
        assertEquals(USER_NOT_ACTIVE.getDetail(), exception.getMessage());
        verify(userRepository).findByEmailAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Login OK")
    void loginOk() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(
                Optional.of(TestUtil.createValidUser()));
        when(userRepository.save(any(User.class))).thenReturn(TestUtil.createValidUserWithOnePhone());

        var response = userService.login(TestUtil.createValidLoginRequest());

        assertEquals(TestUtil.VALID_UUID, response.getId());
        assertEquals(Boolean.TRUE, response.getIsActive());

        verify(userRepository).findByEmailAndPassword(anyString(), anyString());
        verify(userRepository).save(any(User.class));
    }

}