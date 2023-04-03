package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthLoginRequest;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.integration.auth.AuthGateway;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final AuthGateway authGateway;

    private final UserPersistanceOutput userPersistanceOutput;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserAuthRegisterRequest userAuthRegisterRequest) {
        authGateway.register(userAuthRegisterRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@Valid @RequestBody UserAuthLoginRequest userAuthLoginRequest) {
        User user = userPersistanceOutput.findUser(userAuthLoginRequest.getEmail());
        userAuthLoginRequest.setId(user.getId().getValue());
        userAuthLoginRequest.setName(user.getName());

        Map<String, Object> jwtTokenResponse = authGateway.login(userAuthLoginRequest);

        return ResponseEntity.status(HttpStatus.OK.value()).body(jwtTokenResponse);
    }

    @GetMapping("/validate-username/{username}")
    public ResponseEntity<Boolean> validateUserName(@Valid @PathVariable String username) {
        Boolean userNameValidationResponse = authGateway.validateUserName(username);

        return ResponseEntity.status(HttpStatus.OK.value()).body(userNameValidationResponse);
    }
}


