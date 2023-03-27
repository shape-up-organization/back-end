package br.com.shapeup.core.usecase.user;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.FriendsInput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;

public class FriendsUsecase implements FriendsInput {
    private UserPersistanceOutput userPersistanceOutput;

    @Override
    public void addNewFriend(String currentUsername, String newFriendUsername) {
        // TODO implementar validacoes
        // TODO corrigir implementacao de como vai adicionar na lista de amigos
        User newFriend = userPersistanceOutput.findUserByUsername(newFriendUsername);
        currentUser.getFriends().add(newFriend);
    }
}
