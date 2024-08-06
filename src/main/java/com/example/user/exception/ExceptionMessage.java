package com.example.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    GENERIC(5000, "Error del sistema"),
    USER_EXISTS(4000, "Usuario ya existe"),
    USER_NOT_EXISTS_BY_EMAIL_AND_PASSWORD(4001, "Usuario no existe para el email y password ingresados"),
    USER_NOT_ACTIVE(4002, "Usuario no activo");

    private final int code;
    private final String detail;

}
