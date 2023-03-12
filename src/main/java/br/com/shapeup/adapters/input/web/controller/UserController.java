package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserPersistanceInput userPersistanceInput;

    @Autowired
    private UserHttpMapper userHttpMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequest userRequest) {
        var user = userHttpMapper.toUser(userRequest);
        userPersistanceInput.save(user);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping("/name")
    public ResponseEntity<Void> updateName(@RequestBody @Valid UserNameRequest userNameRequest) {
        var user = userHttpMapper.toUser(userNameRequest);
        userPersistanceInput.updateName(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/last-name")
    public ResponseEntity<Void> updateLastName(@RequestBody @Valid UserLastNameRequest userLastNameRequest) {
        var user = userHttpMapper.toUser(userLastNameRequest);
        userPersistanceInput.updateLastName(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/cell-phone")
    public ResponseEntity<Void> updateCellPhone(@RequestBody @Valid UserCellphoneRequest userCellphoneRequest) {
        var user = userHttpMapper.toUser(userCellphoneRequest);
        userPersistanceInput.updateCellPhone(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/biography")
    public ResponseEntity<Void> updateBiography(@RequestBody @Valid UserBiographyRequest userBiographyRequest) {
        var user = userHttpMapper.toUser(userBiographyRequest);
        userPersistanceInput.updateBiography(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/birth")
    public ResponseEntity<Void> updateBirth(@RequestBody @Valid UserBirthRequest userBirthRequest) {
        var user = userHttpMapper.toUser(userBirthRequest);
        userPersistanceInput.updateBirth(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
        var user = userHttpMapper.toUser(userPasswordRequest);
        userPersistanceInput.updatePassword(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
