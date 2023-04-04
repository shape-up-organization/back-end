package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.user.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserPasswordRequest;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.validation.Valid;
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

    private final UserHttpMapper userHttpMapper;

    @DeleteMapping()
    public ResponseEntity<Void> deleteByEmail(@RequestHeader(value = "Authorization")
                                              String token) {
        String jwtToken = token.replace("Bearer ", "");
        var email = JwtService.extractEmailFromToken(jwtToken);
        userPersistanceInput.deleteByEmail(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateUserField(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody UserRequest userRequest) {
        String jwtToken = token.replace("Bearer ", "");
        var email = JwtService.extractEmailFromToken(jwtToken);

        userPersistanceInput.updateUser(email, userRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
