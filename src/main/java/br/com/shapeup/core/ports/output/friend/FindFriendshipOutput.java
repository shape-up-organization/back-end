package br.com.shapeup.core.ports.output.friend;

import br.com.shapeup.core.domain.friend.FriendshipRequest;

public interface FindFriendshipOutput {
    void hasNotSentFriendRequestYet(String usernameSender, String usernameReceiver);
    FriendshipRequest findFriendshipRequest(String usernameSender, String usernameReceiver);
}
