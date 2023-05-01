package br.com.shapeup.core.ports.output.friend;

import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface FriendshipOutput {
    FriendshipRequest sendFriendRequest(User currentUser, User newFriend);

    FriendshipRequest acceptFriendRequest(User currentUser, User newFriend);

    List<User> getAllFriendship(User currentUser);

    void deleteFriendshipRequest(User user, User newFriend);

    void deleteFriend(User user, User newFriend);
}
