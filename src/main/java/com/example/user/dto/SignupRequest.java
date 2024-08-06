package com.example.user.dto;

import com.example.user.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String name;

    @NotBlank(message = "email es requerido")
    @Email(message = "email es inválido", regexp = Constant.EMAIL_PATTERN)
    private String email;

    @NotBlank(message = "password es requerido")
    @Pattern(
            message = "pasword es inválido, solo puede contener 1 mayúscula, 2 números, letras minúsculas, entre 8 y 12 caracteres",
            regexp = Constant.PASSWORD_PATTERN)
    private String password;

    private List<PhoneDTO> phones;

}
