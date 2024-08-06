package com.example.user.exception;

import com.example.user.dto.ErrorResponse.ErrorDTO;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserException extends RuntimeException {

    private final ErrorDTO errorDTO;

    public UserException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getDetail());
        this.errorDTO = ErrorDTO.builder()
                                .code(exceptionMessage.getCode())
                                .detail(exceptionMessage.getDetail())
                                .timestamp(LocalDateTime.now())
                                .build();
    }

}
