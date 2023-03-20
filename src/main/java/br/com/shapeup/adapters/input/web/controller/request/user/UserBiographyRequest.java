package br.com.shapeup.adapters.input.web.controller.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserBiographyRequest {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotNull(message = "Biography is required")
    @NotBlank(message = "Biography is required")
    private String biography;
}