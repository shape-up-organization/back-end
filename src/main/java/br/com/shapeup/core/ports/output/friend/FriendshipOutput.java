package br.com.shapeup.core.ports.output.friend;

import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;

public interface FriendshipOutput {
    FriendshipRequest sendFriendRequest(User currentUser, User newFriend);

    FriendshipRequest acceptFriendRequest(User currentUser, User newFriend);

    void deleteFriendshipRequest(User user, User newFriend);

    void deleteFriend(User user, User newFriend);
}
