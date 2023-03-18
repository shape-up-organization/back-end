package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.user.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthLoginRequest;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.integration.auth.AuthGateway;
import br.com.shapeup.common.config.security.service.JwtService;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserHttpMapper userHttpMapper;

    private final UserPersistanceInput userPersistanceInput;
    private final AuthGateway authGateway;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserAuthRegisterRequest userAuthRegisterRequest) {
        authGateway.register(userAuthRegisterRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@RequestBody UserAuthLoginRequest userAuthRegisterRequest) {

        return ResponseEntity.status(HttpStatus.OK.value()).body(authGateway.login(userAuthRegisterRequest));
    }
}
