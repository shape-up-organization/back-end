package br.com.shapeup.adapters.input.web.controller.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserBirthRequest {
    private String email;

    @Pattern(
            regexp = "^[1-9]{2}9?[6-9][0-9]{3}[0-9]{4}$",
            message = "Invalid cell phone number"
    )
    @NotBlank
    private String birth;
}
