package br.com.shapeup.core.ports.input.friend;

import br.com.shapeup.core.domain.friend.FriendshipRequest;

public interface FriendshipInput {

    FriendshipRequest sendFriendRequest(String newFriendUsername, String email);

    FriendshipRequest acceptFriendRequest(String friendUsername, String email);

    void deleteFriendshipRequest(String friendUsername, String email);

    void deleteFriend(String friendUsername,String email);
}
