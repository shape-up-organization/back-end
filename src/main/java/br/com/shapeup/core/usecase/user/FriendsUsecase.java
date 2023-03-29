package br.com.shapeup.core.usecase.user;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.FriendsInput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.FriendsOutput;

public class FriendsUsecase implements FriendsInput {
    private final FriendsOutput friendsOutput;
    private final FindUserOutput findUserOutput;

    public FriendsUsecase(FriendsOutput friendsOutput, FindUserOutput findUserOutput) {
        this.friendsOutput = friendsOutput;
        this.findUserOutput = findUserOutput;
    }


    @Override
    public void addNewFriend(String newFriendUsername, String email) {
        User user = findUserOutput.findByEmail(email);
        User newFriend = findUserOutput.findByUsername(newFriendUsername);

        user.getFriends().add(newFriend);
    }
}
