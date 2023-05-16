package br.com.shapeup.adapters.input.web.controller.mapper.user;

import br.com.shapeup.adapters.input.web.controller.response.user.UserResponse;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.core.domain.user.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UserHttpMapper {

    UserResponse userToUserResponse(User userSearch, FriendshipStatus friendshipStatus);

    List<UserResponse> usersToUserResponses(List<User> searchUsers, List<FriendshipStatus> friendshipStatus);

    List<String> usersToUsernames(List<User> searchUsers);
}
