package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.ports.input.user.FriendsInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsInput friendsInput;

    @PostMapping("/add-friend/{newFriendUsername}")
    public ResponseEntity<Void> addNewFriend(
            @RequestHeader(value = "Authorization") String token,
            @Valid @PathVariable String newFriendUsername
    ) {
        String email = JwtService.extractEmailFromToken(token);

        friendsInput.addNewFriend(newFriendUsername, email);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}