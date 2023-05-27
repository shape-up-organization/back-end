package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.friend.FriendshipHttpMapper;
import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.ListFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.friend.FriendshipInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shapeup/friends")
public class FriendshipController {

    private final FriendshipInput friendshipInput;
    private final FriendshipHttpMapper friendshipHttpMapper;

    @PostMapping("/sent-friendship-request/{newFriendUsername}")
    public ResponseEntity<RequestFriendshipResponse> sentFriendshipRequest(
            HttpServletRequest request,
            @Valid @PathVariable String newFriendUsername
    ) {
        String token = TokenUtils.getToken(request);
        String email = JwtService.extractEmailFromToken(token);

        FriendshipRequest friendRequest = friendshipInput.sendFriendRequest(newFriendUsername, email);
        RequestFriendshipResponse requestFriendshipResponse = friendshipHttpMapper
                .friendRequestToRequestFriendshipResponse(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(requestFriendshipResponse);
    }

    @PostMapping("/accept-friendship-request/{friendUsername}")
    public ResponseEntity<AcceptedFriendshipResponse> acceptFriendshipRequest(
            HttpServletRequest request,
            @Valid @PathVariable String friendUsername
    ) {
        String token = TokenUtils.getToken(request);
        String email = JwtService.extractEmailFromToken(token);

        FriendshipRequest friendRequest = friendshipInput.acceptFriendRequest(friendUsername, email);
        AcceptedFriendshipResponse acceptedFriendshipRequest = friendshipHttpMapper
                .friendRequestToAcceptedFriendshipResponse(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(acceptedFriendshipRequest);
    }

    @GetMapping("/get-all-friendship")
    public ResponseEntity<List<ListFriendshipResponse>> getAllFriendship(
            HttpServletRequest request
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        List<User> friendshipRequests = friendshipInput.getAllFriendship(username);
        List<ListFriendshipResponse> listFriendshipResponses = friendshipHttpMapper.usersToListFriendshipResponse(friendshipRequests);

        return ResponseEntity.status(HttpStatus.OK.value()).body(listFriendshipResponses);
    }
    @DeleteMapping("/delete-friendship-request/{friendUsername}")
    public ResponseEntity<Void> deleteFriendshipRequest(HttpServletRequest request, @Valid @PathVariable String friendUsername) {
        String token = TokenUtils.getToken(request);
        String email = JwtService.extractEmailFromToken(token);

        friendshipInput.deleteFriendshipRequest(friendUsername, email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete-friend/{friendUsername}")
    public ResponseEntity<Void> deleteFriendshipByUsername(HttpServletRequest request, @Valid @PathVariable String friendUsername) {
        String token = TokenUtils.getToken(request);
        String email = JwtService.extractEmailFromToken(token);

        friendshipInput.deleteFriend(friendUsername, email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
