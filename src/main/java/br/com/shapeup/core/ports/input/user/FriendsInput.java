package br.com.shapeup.core.ports.input.user;

import br.com.shapeup.core.domain.user.User;

public interface FriendsInput {

    void addNewFriend(String newFriendUsername, String email);
}
