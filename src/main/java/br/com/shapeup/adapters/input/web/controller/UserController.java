package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.user.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.input.web.controller.response.user.UserFieldsUpdateResponse;
import br.com.shapeup.adapters.input.web.controller.response.user.UserResponse;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
@RequestMapping(value = "/shapeup/users")
public class UserController {

    private final UserPersistanceInput userPersistanceInput;
    private final UserHttpMapper userHttpMapper;

    @DeleteMapping()
    public ResponseEntity<Void> deleteByEmail(HttpServletRequest request) {

        String jwtToken = TokenUtils.getToken(request);
        String email = JwtService.extractEmailFromToken(jwtToken);

        userPersistanceInput.deleteByEmail(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping()
    public ResponseEntity<UserFieldsUpdateResponse> updateUserField(
            HttpServletRequest request,
            @RequestBody UserRequest userRequest
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        userPersistanceInput.updateUser(email, userRequest);
        String newToken = TokenUtils.updateUserAndGenerateNewToken(jwtToken, userRequest);

        return ResponseEntity.status(HttpStatus.OK.value()).body(
                UserFieldsUpdateResponse
                        .builder()
                        .token(newToken)
                        .updatedAt(String.valueOf(LocalDateTime.now()))
                        .build()
        );
    }

    @GetMapping("/search-username/{username}")
    public ResponseEntity<List<UserResponse>> findAllUserByUsername(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        List<User> searchUsers = userPersistanceInput.findAllUserByUsername(username);
        return getListResponseEntity(email, searchUsers);
    }

    @GetMapping("/search-fullname")
    public ResponseEntity<List<UserResponse>> findAllUserByFullName(
            @RequestParam String fullName,
            HttpServletRequest request
    ) {
        String jwtToken = TokenUtils.getToken(request);
        var email = JwtService.extractEmailFromToken(jwtToken);

        List<User> searchUsers = userPersistanceInput.findAllUserByFullName(fullName);
        return getListResponseEntity(email, searchUsers);
    }

    @GetMapping("/find-username/{username}")
    public ResponseEntity<UserResponse> findAnUserByUsername(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String jwtToken = TokenUtils.getToken(request);

        User user = userPersistanceInput.findUserByUsername(username);

        FriendshipStatus friendshipStatus = userPersistanceInput.getFriendshipStatus(
                JwtService.extractEmailFromToken(jwtToken),
                List.of(username)
        ).get(0);

        Optional<UserResponse> userResponse = Optional.of(userHttpMapper.userToUserResponse(user, friendshipStatus));

        return ResponseEntity.of(userResponse);
    }

    private ResponseEntity<List<UserResponse>> getListResponseEntity(String email, List<User> searchUsers) {
        List<String> usernames = userHttpMapper.usersToUsernames(searchUsers);
        List<FriendshipStatus> friendshipStatus = userPersistanceInput.getFriendshipStatus(email, usernames);

        List<UserResponse> userResponses = userHttpMapper.usersToUserResponses(searchUsers, friendshipStatus);

        return ResponseEntity.status(HttpStatus.OK.value()).body(userResponses);
    }

    @GetMapping("/user-xp")
    public ResponseEntity<Long> getUserXp(HttpServletRequest request) {
        String jwtToken = TokenUtils.getToken(request);
        var username = JwtService.extractAccountNameFromToken(jwtToken);

        Long xp = userPersistanceInput.getUserXp(username);

        return ResponseEntity.status(HttpStatus.OK.value()).body(xp);
    }
}
