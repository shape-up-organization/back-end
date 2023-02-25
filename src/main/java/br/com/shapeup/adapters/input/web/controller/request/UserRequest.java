package br.com.shapeup.adapters.input.web.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest{
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String  lastName;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String cellPhone;
    @NotNull
    @NotBlank
    private String password;
}
