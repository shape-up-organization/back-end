package br.com.shapeup.adapters.input.web.controller.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAuthLoginRequest {
    @Email(message = "Email is invalid")
    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    String email;
    @NotNull(message = "Password is required")
    String password;
}
