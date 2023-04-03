package br.com.shapeup.adapters.input.web.controller.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAuthRegisterRequest {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 45)
    String name;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 45)
    String lastName;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 45)
    @Pattern( regexp = "^[^\\s@]+$", message = "Username cannot contain spaces or @" )
    String username;
    @NotNull
    @NotBlank
    @Email
    @Size(min = 2, max = 45)
    String email;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 45)
    String password;
    @NotNull
    @NotBlank
    String birth;
    @NotNull
    @NotBlank
    String cellPhone;
}


