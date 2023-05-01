package br.com.shapeup.adapters.input.web.controller.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {
    @Email
    @NotBlank
    private String email;

    @Size(min = 2, max = 45)
    @NotBlank
    private String name;

    @Size(min = 2, max = 45)
    @NotBlank
    private String lastName;

    @NotBlank
    private String cellPhone;

    @NotBlank
    @Past
    private LocalDate birth;

    @NotBlank
    private String biography;

    @Pattern(regexp = "[*^\\s@]")
    @Size(min = 2, max = 45)
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
