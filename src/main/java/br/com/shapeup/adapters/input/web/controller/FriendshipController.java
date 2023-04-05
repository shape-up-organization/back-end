package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.friend.FriendshipHttpMapper;
import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.ports.input.friend.FriendshipInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipInput friendshipInput;
    private final FriendshipHttpMapper friendshipHttpMapper;

    @PostMapping("/sent-friendship-request/{newFriendUsername}")
    public ResponseEntity<RequestFriendshipResponse> sentFriendshipRequest(
            HttpServletRequest request,
            @Valid @PathVariable String newFriendUsername
    ) {
        String token = request.getHeader("Authorization").substring(7);
        String email = JwtService.extractEmailFromToken(token);

        FriendshipRequest friendRequest = friendshipInput.sendFriendRequest(newFriendUsername, email);
        RequestFriendshipResponse requestFriendshipResponse = friendshipHttpMapper.friendRequestToRequestFriendshipResponse(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(requestFriendshipResponse);
    }

    @PostMapping("/accept-friendship-request/{friendUsername}")
    public ResponseEntity<AcceptedFriendshipResponse> acceptFriendshipRequest(
            HttpServletRequest request,
            @Valid @PathVariable String friendUsername
    ) {
        String token = request.getHeader("Authorization").substring(7);
        String email = JwtService.extractEmailFromToken(token);

        FriendshipRequest friendRequest = friendshipInput.acceptFriendRequest(friendUsername, email);
        AcceptedFriendshipResponse acceptedFriendshipRequest = friendshipHttpMapper.friendRequestToAcceptedFriendshipResponse(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(acceptedFriendshipRequest);
    }
}