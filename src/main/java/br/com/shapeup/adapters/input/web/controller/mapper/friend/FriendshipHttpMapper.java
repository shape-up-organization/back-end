package br.com.shapeup.adapters.input.web.controller.mapper.friend;


import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.ListFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface FriendshipHttpMapper {

    RequestFriendshipResponse friendRequestToRequestFriendshipResponse(FriendshipRequest friendshipRequest);

    AcceptedFriendshipResponse friendRequestToAcceptedFriendshipResponse(FriendshipRequest friendshipRequest);

    List<ListFriendshipResponse> usersToListFriendshipResponse(List<User> users);

}
