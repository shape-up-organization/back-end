package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserPersistanceInput userPersistanceInput;

    @Autowired
    private UserHttpMapper userHttpMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequest userRequest) {
        var user = userHttpMapper.toUser(userRequest);
        userPersistanceInput.save(user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
        var user = userHttpMapper.toUser(userPasswordRequest);
        userPersistanceInput.updatePassword(user);

        return ResponseEntity.ok().build();
    }
}
