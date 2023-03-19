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

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    String lastName;
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    String username;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    String password;
    @NotNull(message = "Birth is required")
    @NotBlank(message = "Birth is required")
    String birth;
    @NotNull(message = "Cellphone is required")
    @NotBlank(message = "Cellphone is required")
    String cellPhone;
}