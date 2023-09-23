package br.com.shapeup.adapters.input.web.controller.mapper.friend;

import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.ListFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.adapters.output.repository.mongo.chat.ChatMongoRepository;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class FriendShipHttpMapperImpl implements FriendshipHttpMapper {

    private final ChatMongoRepository chatMongoRepository;

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

    @Override
    public List<ListFriendshipResponse> usersToListFriendshipResponse(List<User> users) {

        return users.stream()
                .map(user -> ListFriendshipResponse.builder()
                        .id(user.getId().getValue())
                        .firstName(user.getFullName().getFirstName())
                        .lastName(user.getFullName().getLastName())
                        .fullName(user.getFullName().get())
                        .username(user.getUsername())
                        .xp(user.getXp())
                        .profilePicture(user.getProfilePicture())
                        .build()).toList();
    }
}
