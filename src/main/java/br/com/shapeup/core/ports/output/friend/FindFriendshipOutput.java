package br.com.shapeup.core.ports.output.friend;

import br.com.shapeup.core.domain.friend.FriendshipRequest;
import java.util.List;

public interface FindFriendshipOutput {
    void hasNotSentFriendRequestYet(String usernameSender, String usernameReceiver);
    FriendshipRequest findFriendshipRequest(String usernameSender, String usernameReceiver);
    Boolean hasSentFriendRequest(String usernameSender, String usernameReceiver);
    List<FriendshipRequest> findAllFriendshipRequest(String usernameSender, String usernameReceiver);
    FriendshipRequest findAllFriendshipRequestAcceptedFalse(String usernameSender, String usernameReceiver, Boolean accepted);
}
