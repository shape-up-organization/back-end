package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
public class UserController {
    private UserPersistanceInput userPersistanceInput;

    private UserHttpMapper userHttpMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequest userRequest) {
        var user = userHttpMapper.toUser(userRequest);
        userPersistanceInput.save(user);

        return ResponseEntity.ok().build();
    }
}
