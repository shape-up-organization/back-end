package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class UserController {
    private final UserPersistanceInput userPersistanceInput;

    @DeleteMapping()
    public ResponseEntity<Void> deleteByEmail(HttpServletRequest request) {

        String jwtToken = TokenUtils.getToken(request);

        var email = JwtService.extractEmailFromToken(jwtToken);
        userPersistanceInput.deleteByEmail(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateUserField(
            HttpServletRequest request,
            @RequestBody UserRequest userRequest
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        userPersistanceInput.updateUser(email, userRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
