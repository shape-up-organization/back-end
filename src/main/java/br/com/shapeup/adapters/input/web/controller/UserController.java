package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
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

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
        var user = userHttpMapper.toUser(userPasswordRequest);
        userPersistanceInput.updatePassword(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable String email) {

        userPersistanceInput.deleteByEmail(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
