package br.com.shapeup.core.usecase.friend;

import br.com.shapeup.common.exceptions.friend.AddYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.AlreadyFriendException;
import br.com.shapeup.common.exceptions.friend.DeleteYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.DuplicateFriendshipException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestAlreadyAcceptedException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestNotFoundException;
import br.com.shapeup.common.exceptions.friend.NotFriendException;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.friend.FriendshipInput;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;

import io.vavr.control.Try;
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

        //event sendFriendRequest

        return friendsOutput.sendFriendRequest(user, newFriend);
    }

    @Override
    public FriendshipRequest acceptFriendRequest(String friendUsername, String email) {

        User user = findUserOutput.findByEmail(email);
        User friend = findUserOutput.findByUsername(friendUsername);
        return Try.of(() -> findFriendshipOutput.findFriendshipRequest(friend.getUsername(), user.getUsername()))
                .onFailure(throwable -> {
                    throw new FriendshipRequestNotFoundException("Friendship request not found or already accepted");
                })
                .onSuccess(friendshipRequest -> {
                    validateUserAlreadyFriend(friendUsername, user);
                    verifyFriendshipRequestValidity(user, friend, friendshipRequest);
                    verifyFriendshipRequestAlreadyAccepted(friendshipRequest);

                    friendsOutput.acceptFriendRequest(user, friend);
                    friendshipRequest.setAccepted(true);

                    deleteOldFriendshipRequest(user, friend);
                }).get();
    }

    private void deleteOldFriendshipRequest(User user, User friend) {
        var oldFriendshipRequestWithNotAccepted = findFriendshipOutput.findFriendshipRequest(friend.getUsername(), user.getUsername());

        if (oldFriendshipRequestWithNotAccepted.getAccepted().equals(false)) {
            deleteFriendshipRequest(friend.getUsername(), user.getEmail().getValue());
        }
    }

    @Override
    public List<User> getAllFriendship(String username) {

        validateHasDuplicateFriendship(username, findUserOutput);
        User currentUser = findUserOutput.findByUsername(username);

        return friendsOutput.getAllFriendship(currentUser);
    }

    @Override
    public void deleteFriendshipRequest(String friendUsername, String email) {

        User user = findUserOutput.findByEmail(email);
        User newFriend = findUserOutput.findByUsername(friendUsername);

        var friendshipRequestsNotAccepeted = findFriendshipOutput.findAllFriendshipRequestAcceptedFalse(newFriend.getUsername(), user.getUsername(), false);

        validateFriendshipRequestExists(user, newFriend);
        validateDeleteIsSameUser(friendUsername, user);
        validateUserAlreadyFriend(friendUsername, user);

        friendsOutput.deleteFriendshipRequest(friendshipRequestsNotAccepeted.getId().getValue());
    }

    @Override
    public void deleteFriend(String friendUsername, String email) {
        User user = findUserOutput.findByEmail(email);
        User newFriend = findUserOutput.findByUsername(friendUsername);

        List<User> friends = friendsOutput.getAllFriendship(user);
        user.setFriends(friends);

        validateDeleteIsSameUser(friendUsername, user);
        validateExistsFriendship(friendUsername, user);

        friendsOutput.deleteFriend(user, newFriend);
    }

    private void validateUserAlreadyFriend(String newFriendUsername, User user) {
        boolean isAlreadyFriend = user.getFriends().stream()
                .anyMatch(friend -> friend.getUsername().equals(newFriendUsername));

        if (isAlreadyFriend) {
            throw new AlreadyFriendException(user.getUsername());
        }
    }

    private void validateIsSameUser(String newFriendUsername, User user) {
        boolean isSameUser = user.getUsername().equals(newFriendUsername);

        if (isSameUser) {
            throw new AddYourselfAsAFriendException();
        }
    }

    private void validateDeleteIsSameUser(String newFriendUsername, User user) {
        boolean isSameUser = user.getUsername().equals(newFriendUsername);

        if (isSameUser) {
            throw new DeleteYourselfAsAFriendException();
        }
    }

    private void verifyFriendshipRequestValidity(User user, User friend, FriendshipRequest friendshipRequest) {
        boolean isSentByCurrentUser = friendshipRequest.getUsernameSender().equals(user.getUsername());
        boolean isReceivedByFriend = friendshipRequest.getUsernameReceiver().equals(friend.getUsername());

        if (isSentByCurrentUser || isReceivedByFriend) {
            throw new FriendshipRequestNotFoundException();
        }
    }

    private void validateHasDuplicateFriendship(String username, FindUserOutput findUserOutput) {
        var friends = findUserOutput.findByUsername(username).getFriends();

        boolean hasDuplicateFriendship = friends.stream()
                .anyMatch(friend -> friend.getUsername().equals(username));

        if (hasDuplicateFriendship) {
            throw new DuplicateFriendshipException(username);
        }
    }


    private void validateExistsFriendship(String friendUsername, User user) {
        boolean isAlreadyFriend = user.getFriends().stream()
                .anyMatch(friend -> friend.getUsername().equals(friendUsername));

        if (!isAlreadyFriend) {
            throw new NotFriendException(user.getUsername()); // depois tera uma exception dizendo que nao é amigo
        }
    }

    private void verifyFriendshipRequestAlreadyAccepted(FriendshipRequest friendshipRequest) {

        if (friendshipRequest.getAccepted().equals(true)) {
            throw new FriendshipRequestAlreadyAcceptedException();
        }
    }

    private void validateFriendshipRequestExists(User user, User newFriend) {
        Boolean hasSentFriendRequest = findFriendshipOutput
                .hasSentFriendRequest(newFriend.getUsername(), user.getUsername());

        if (!hasSentFriendRequest) {
            throw new FriendshipRequestNotFoundException();
        }
    }

}

