package br.com.shapeup.adapters.input.web.controller.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequest {
    @Email
    private String email;
    private String password;
}
