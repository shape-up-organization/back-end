package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.AuthRequest;
import br.com.shapeup.adapters.input.web.controller.response.AuthResponse;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.common.config.security.config.JWtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final JWtTokenUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JWtTokenUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            UserEntity user = (UserEntity) authenticationManager.authenticate(authentication).getPrincipal();
            String token = jwtUtil.generateAccessToken(user);
            AuthResponse authResponse = new AuthResponse(user.getUsername(), token);
            return ResponseEntity.ok(authResponse);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.fillInStackTrace().getMessage());
        }
    }
}
