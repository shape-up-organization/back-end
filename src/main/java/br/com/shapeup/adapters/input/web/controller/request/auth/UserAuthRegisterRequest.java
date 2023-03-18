package br.com.shapeup.adapters.input.web.controller.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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