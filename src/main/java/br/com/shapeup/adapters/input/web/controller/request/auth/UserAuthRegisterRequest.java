package br.com.shapeup.adapters.input.web.controller.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAuthRegisterRequest {

    @NotNull
    @NotBlank
    String name;
    @NotNull
    @NotBlank
    String lastName;
    @NotNull
    @NotBlank
    String username;
    @NotNull
    @NotBlank
    @Email
    String email;
    @NotNull
    @NotBlank
    String password;
    @NotNull
    @NotBlank
    String birth;
    @NotNull
    @NotBlank
    String cellPhone;
}


