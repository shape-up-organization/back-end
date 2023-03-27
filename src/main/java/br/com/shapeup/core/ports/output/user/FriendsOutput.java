package br.com.shapeup.core.ports.output.user;

import br.com.shapeup.core.domain.user.User;

public interface FriendsOutput {
    void addNewUser(User currentUser, User newFriend);
}
