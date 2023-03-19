package br.com.shapeup.adapters.input.web.controller.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserPasswordRequest {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
}
