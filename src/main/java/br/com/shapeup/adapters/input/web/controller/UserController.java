package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.user.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.input.web.controller.response.user.UserResponse;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "users")
public class UserController {

    private final UserPersistanceInput userPersistanceInput;
    private final UserHttpMapper userHttpMapper;

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

    @GetMapping("/search-username/{username}")
    public ResponseEntity<UserResponse> findUserByUsername(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        FriendshipStatus friendshipStatus = userPersistanceInput.getFriendshipStatus(email, username);
        User searchUser = userPersistanceInput.findUserByUsername(username);

        UserResponse userResponse = userHttpMapper.userToUserResponse(searchUser, friendshipStatus);

        return ResponseEntity.status(HttpStatus.OK.value()).body(userResponse);
    }

    @GetMapping("/search-fullname")
    public ResponseEntity<List<UserResponse>> findAllUseraByFullName(
            @RequestParam String name,
            @RequestParam String lastName,
            HttpServletRequest request
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        List<User> searchUsers = userPersistanceInput.findAllUserByFullName(name, lastName);
        List<String> usernames = userHttpMapper.usersToUsernames(searchUsers);
        List<FriendshipStatus> friendshipStatus = userPersistanceInput.getFriendshipStatus(email, usernames);

        List<UserResponse> userResponses = userHttpMapper.usersToUserResponses(searchUsers, friendshipStatus);

        return ResponseEntity.status(HttpStatus.OK.value()).body(userResponses);
    }
}
