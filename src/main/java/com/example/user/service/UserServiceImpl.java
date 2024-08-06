package com.example.user.service;

import com.example.user.dto.LoginRequest;
import com.example.user.dto.LoginResponse;
import com.example.user.dto.PhoneDTO;
import com.example.user.dto.SignupRequest;
import com.example.user.dto.SignupResponse;
import com.example.user.exception.UserException;
import com.example.user.model.Phone;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.user.exception.ExceptionMessage.USER_EXISTS;
import static com.example.user.exception.ExceptionMessage.USER_NOT_ACTIVE;
import static com.example.user.exception.ExceptionMessage.USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public SignupResponse signup(SignupRequest signupRequest) {
        var email = signupRequest.getEmail();
        log.info("Signing up user: {}", email);
        log.debug("signupRequest: {}", signupRequest);

        if (this.userRepository.existsByEmail(email)) {
            log.error("User {} already exists", email);
            throw new UserException(USER_EXISTS);
        }

        var phones =
                Objects.nonNull(signupRequest.getPhones()) ? signupRequest.getPhones().stream()
                                                                          .map(phoneDTO -> Phone
                                                                                  .builder()
                                                                                  .number(phoneDTO.getNumber())
                                                                                  .cityCode(phoneDTO.getCityCode())
                                                                                  .countryCode(
                                                                                          phoneDTO.getCountryCode())
                                                                                  //.user(user)
                                                                                  .build())
                                                                          .collect(Collectors.toList()) :
                new ArrayList<Phone>();

        var user = User.builder()
                       .name(signupRequest.getName())
                       .email(email)
                       .password(signupRequest.getPassword())
                       .lastLogin(LocalDateTime.now())
                       //.token("atoken")
                       .active(Boolean.TRUE)
                       .phones(phones)
                       .build();

        var savedUser = this.userRepository.save(user);

        return SignupResponse.builder()
                             .id(savedUser.getId())
                             .created(savedUser.getCreated())
                             .lastLogin(savedUser.getLastLogin())
                             .token(savedUser.getToken())
                             .isActive(savedUser.getActive())
                             .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var email = loginRequest.getEmail();
        log.info("Logging in user with email: {}", email);
        log.debug("loginRequest: {}", loginRequest);

        User user = this.userRepository.findByEmailAndPassword(email, loginRequest.getPassword())
                                       .orElseThrow(() -> new UserException(USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new UserException(USER_NOT_ACTIVE);
        }

        user.setLastLogin(LocalDateTime.now());
        var updatedUser = this.userRepository.save(user);

        var phoneDTOS = updatedUser.getPhones().stream()
                                   .map(phone -> PhoneDTO.builder()
                                                         .number(phone.getNumber())
                                                         .cityCode(phone.getCityCode())
                                                         .countryCode(phone.getCountryCode())
                                                         .build())
                                   .collect(Collectors.toList());

        return LoginResponse.builder()
                            .id(updatedUser.getId())
                            .created(updatedUser.getCreated())
                            .lastLogin(updatedUser.getLastLogin())
                            .token(updatedUser.getToken())
                            .isActive(updatedUser.getActive())
                            .name(updatedUser.getName())
                            .email(updatedUser.getEmail())
                            .password(updatedUser.getPassword())
                            .phones(phoneDTOS)
                            .build();
    }

}
