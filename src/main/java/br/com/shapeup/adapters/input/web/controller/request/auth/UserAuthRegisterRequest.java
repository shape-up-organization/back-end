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
//    @Past
    String birth;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 11)
    @Pattern(
            regexp = "^[1-9]{2}9?[6-9][0-9]{3}[0-9]{4}$",
            message = "Invalid cell phone number"
    )
    String cellPhone;
}


