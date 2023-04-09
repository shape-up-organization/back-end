package br.com.shapeup.core.usecase.friend;
import br.com.shapeup.common.exceptions.friend.AddYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.AlreadyFriendException;
import br.com.shapeup.common.exceptions.friend.DuplicateFriendshipException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestNotFoundException;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.friend.FriendshipInput;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.util.List;

public class FriendshipUsecase implements FriendshipInput {
    private final FriendshipOutput friendsOutput;
    private final FindUserOutput findUserOutput;
    private final FindFriendshipOutput findFriendshipOutput;

    public FriendshipUsecase(FriendshipOutput friendsOutput, FindUserOutput findUserOutput, FindFriendshipOutput findFriendshipOutput) {
        this.friendsOutput = friendsOutput;
        this.findUserOutput = findUserOutput;
        this.findFriendshipOutput = findFriendshipOutput;
    }

    @Override
    public FriendshipRequest sendFriendRequest(String newFriendUsername, String email) {
        User user = findUserOutput.findByEmail(email);
        User newFriend = findUserOutput.findByUsername(newFriendUsername);

        validateUserAlreadyFriend(newFriendUsername, user);
        validateIsSameUser(newFriendUsername, user);

        findFriendshipOutput.hasNotSentFriendRequestYet(user.getUsername(), newFriend.getUsername());

        return friendsOutput.sendFriendRequest(user, newFriend);
    }

    @Override
    public FriendshipRequest acceptFriendRequest(String friendUsername, String email) {

        User user = findUserOutput.findByEmail(email);
        User friend = findUserOutput.findByUsername(friendUsername);
        FriendshipRequest friendshipRequest = findFriendshipOutput.findFriendshipRequest(friend.getUsername(), user.getUsername());

        validateUserAlreadyFriend(friendUsername, user);
        verifyFriendshipRequestValidity(user, friend, friendshipRequest);

        return friendsOutput.acceptFriendRequest(user, friend);
    }

    @Override
    public List<User> getAllFriendship(String username) {
        validateHasDuplicateFriendship(username, findUserOutput);
        User currentUser = findUserOutput.findByUsername(username);

        return friendsOutput.getAllFriendship(currentUser);
    }

    private static void validateUserAlreadyFriend(String newFriendUsername, User user) {
        boolean isAlreadyFriend = user.getFriends().stream()
                .anyMatch(friend -> friend.getUsername().equals(newFriendUsername));

        if (isAlreadyFriend) {
            throw new AlreadyFriendException(user.getUsername());
        }
    }

    private static void validateIsSameUser(String newFriendUsername, User user) {
        boolean isSameUser = user.getUsername().equals(newFriendUsername);

        if (isSameUser) {
            throw new AddYourselfAsAFriendException();
        }
    }

    private static void verifyFriendshipRequestValidity(User user, User friend, FriendshipRequest friendshipRequest) {
        boolean isSentByCurrentUser = friendshipRequest.getUsernameSender().equals(user.getUsername());
        boolean isReceivedByFriend = friendshipRequest.getUsernameReceiver().equals(friend.getUsername());

        if (isSentByCurrentUser || isReceivedByFriend) {
            throw new FriendshipRequestNotFoundException();
        }
    }

    private static void validateHasDuplicateFriendship(String username, FindUserOutput findUserOutput) {
        var friends = findUserOutput.findByUsername(username).getFriends();

        boolean hasDuplicateFriendship = friends.stream()
                .anyMatch(friend -> friend.getUsername().equals(username));

        if (hasDuplicateFriendship) {
            throw new DuplicateFriendshipException(username);
        }
    }
}
