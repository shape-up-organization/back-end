package br.com.shapeup.adapters.input.web.controller.mapper.user;

import br.com.shapeup.adapters.input.web.controller.response.user.UserResponse;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.core.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHttpMapperImpl implements UserHttpMapper {

    @Override
    public UserResponse userToUserResponse(User userSearch, FriendshipStatus friendshipStatus) {
        return UserResponse.builder()
                .name(userSearch.getName())
                .lastName(userSearch.getLastName())
                .username(userSearch.getUsername())
                .profilePicture(userSearch.getProfilePicture())
                .xp(userSearch.getXp())
                .haveFriendRequest(friendshipStatus.haveFriendRequest())
                .isFriend(friendshipStatus.isFriend())
                .build();
    }

    @Override
    public List<UserResponse> usersToUserResponses(List<User> searchUsers, FriendshipStatus friendshipStatus) {
        return searchUsers.stream()
                .map(user -> userToUserResponse(user, friendshipStatus))
                .toList();
    }

    @Override
    public List<String> usersToUsernames(List<User> searchUsers) {
        return searchUsers.stream()
                .map(User::getUsername)
                .toList();
    }
}
