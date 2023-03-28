package br.com.shapeup.core.usecase.user;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.FriendsInput;
import br.com.shapeup.core.ports.output.user.FriendsOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;

public class FriendsUsecase implements FriendsInput {
    private final FriendsOutput friendsOutput;
    private final UserPersistanceOutput userPersistanceOutput;

    public FriendsUsecase(FriendsOutput friendsOutput, UserPersistanceOutput userPersistanceOutput) {
        this.friendsOutput = friendsOutput;
        this.userPersistanceOutput = userPersistanceOutput;
    }


    @Override
    public void addNewFriend(String newFriendUsername) {

    }
}
