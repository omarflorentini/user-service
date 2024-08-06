package com.example.user.exception;

import com.example.user.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.example.user.dto.ErrorResponse.ErrorDTO;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        log.error("Exception => " + exception.getMessage(), exception);
        var message = ExceptionMessage.GENERIC;
        var error = Collections.singletonList(
                ErrorDTO.builder()
                        .code(message.getCode())
                        .detail(message.getDetail())
                        .timestamp(LocalDateTime.now())
                        .build());
        return ResponseEntity.badRequest()
                             .body(ErrorResponse.builder()
                                                .error(error)
                                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> userExceptionHandler(UserException exception) {
        log.error("UserException => {}", exception.getMessage());
        return ResponseEntity.badRequest()
                             .body(ErrorResponse.builder()
                                                .error(Collections.singletonList(exception.getErrorDTO()))
                                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException => {}", exception.getMessage());

        var now = LocalDateTime.now();
        var errors = exception.getFieldErrors().stream()
                              .map(error -> ErrorDTO.builder()
                                                    //.code(error.hashCode())
                                                    .code(exception.getErrorCount())
                                                    .detail(error.getDefaultMessage())
                                                    .timestamp(now)
                                                    .build())
                              .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ErrorResponse.builder().error(errors).build());
    }

}
