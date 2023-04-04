package br.com.shapeup.adapters.input.web.controller.mapper.friend;

import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FriendshipHttpMapperImpl implements FriendshipHttpMapper {
    @Override
    public RequestFriendshipResponse friendRequestToRequestFriendshipResponse(FriendshipRequest friendshipRequest) {
        return RequestFriendshipResponse.builder()
                .id(friendshipRequest.getId().getValue())
                .usernameSender(friendshipRequest.getUsernameSender())
                .usernameReceiver(friendshipRequest.getUsernameReceiver())
                .accepted(friendshipRequest.getAccepted())
                .build();
    }

    @Override
    public AcceptedFriendshipResponse friendRequestToAcceptedFriendshipResponse(FriendshipRequest friendshipRequest) {
        return AcceptedFriendshipResponse.builder()
                .id(friendshipRequest.getId().getValue())
                .usernameSender(friendshipRequest.getUsernameSender())
                .usernameReceiver(friendshipRequest.getUsernameReceiver())
                .accepted(friendshipRequest.getAccepted())
                .build();
    }
}
