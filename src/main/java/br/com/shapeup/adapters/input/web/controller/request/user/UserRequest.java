package br.com.shapeup.adapters.input.web.controller.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {
    @Email
    private String email;

    @Size(min = 2, max = 45)
    private String name;

    @Size(min = 2, max = 45)
    private String lastName;

    private String cellPhone;

    private String birth;

    private String biography;

    @Pattern(regexp = "[*^\\s@]")
    @Size(min = 2, max = 45)
    private String username;

    private String password;
}
