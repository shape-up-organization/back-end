package br.com.shapeup.adapters.input.web.controller.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthLoggedResponse {
    private String email;
    private String token;
}
