package br.com.shapeup.core.usecase.friend;

import br.com.shapeup.common.domain.dto.UsernameSenderAndUsernameReceiverDto;
import br.com.shapeup.common.exceptions.friend.AddYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.AlreadyFriendException;
import br.com.shapeup.common.exceptions.friend.DeleteYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.DuplicateFriendshipException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestAlreadyAcceptedException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestAlreadyExistsException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestNotFoundException;
import br.com.shapeup.common.exceptions.friend.NotFriendException;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.messages.FriendshipRequestMessage;
import br.com.shapeup.core.ports.input.friend.FriendshipInput;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import br.com.shapeup.core.ports.output.friend.FriendshipRequestPublisherOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FriendshipUsecase implements FriendshipInput {
    private final FriendshipOutput friendsOutput;
    private final FindUserOutput findUserOutput;
    private final FindFriendshipOutput findFriendshipOutput;
    private final FriendshipRequestPublisherOutputPort friendshipRequestPublisherOutputPOrt;

    public FriendshipUsecase(
            FriendshipOutput friendsOutput,
            FindUserOutput findUserOutput,
            FindFriendshipOutput findFriendshipOutput,
            FriendshipRequestPublisherOutputPort friendshipRequestPublisherOutputPOrt
    ) {
        this.friendsOutput = friendsOutput;
        this.findUserOutput = findUserOutput;
        this.findFriendshipOutput = findFriendshipOutput;
        this.friendshipRequestPublisherOutputPOrt = friendshipRequestPublisherOutputPOrt;
    }

    @Override
    public FriendshipRequest sendFriendRequest(String newFriendUsername, String email) {
        User user = findUserOutput.findByEmail(email);
        User newFriend = findUserOutput.findByUsername(newFriendUsername);

        validateUserAlreadyFriend(newFriendUsername, user);
        validateIsSameUser(newFriendUsername, user);
        validateFriendshipRequestAlreadyExistis(user, newFriend);

        findFriendshipOutput.hasNotSentFriendRequestYet(user.getUsername(), newFriend.getUsername());

        Try<FriendshipRequest> result = Try.of(() -> friendsOutput.sendFriendRequest(user, newFriend))
                .andThen(response -> friendshipRequestPublisherOutputPOrt.send(
                        FriendshipRequestMessage.builder()
                                .id(UUID.randomUUID().toString())
                                .userSenderId(user.getId().getValue())
                                .userReceiverId(newFriend.getId().getValue())
                                .usernameSender(user.getUsername())
                                .usernameReceiver(newFriend.getUsername())
                                .createdAt(LocalDateTime.now())
                                .build()
                ))
                .onFailure(e -> System.err.println("Error when trying send friendship request: " + e.getCause()));

        if(result.isSuccess()) {
            return result.get();
        }

        throw new RuntimeException("Error when trying send friendship request");
    }

    @Override
    public FriendshipRequest acceptFriendRequest(String friendUsername, String email) {

        User user = findUserOutput.findByEmail(email);
        User friend = findUserOutput.findByUsername(friendUsername);

        var usernameSenderAndUsernameReceiverDto = findFriendshipOutput.findFriendshipRequestByUsername(user, friend);
        User userSender = findUserOutput.findByUsername(usernameSenderAndUsernameReceiverDto.getUsernameSender());
        User userReceiver = findUserOutput.findByUsername(usernameSenderAndUsernameReceiverDto.getUsernameReceiver());

        return Try.of(() -> findFriendshipOutput.findFriendshipRequest(usernameSenderAndUsernameReceiverDto.getUsernameSender(), usernameSenderAndUsernameReceiverDto.getUsernameReceiver()))
                .onFailure(throwable -> {
                    throw new FriendshipRequestNotFoundException("Friendship request not found or already accepted");
                })
                .onSuccess(friendshipRequest -> {
                    validateUserAlreadyFriend(friendUsername, user);
                    verifyFriendshipRequestValidity(user, friend, friendshipRequest);
                    verifyFriendshipRequestAlreadyAccepted(friendshipRequest);

                    friendsOutput.acceptFriendRequest(userSender, userReceiver);
                    friendshipRequest.setAccepted(true);

                    deleteOldFriendshipRequest(userSender, userReceiver);
                }).get();
    }

    private void deleteOldFriendshipRequest(User user, User friend) {
        var oldFriendshipRequestWithNotAccepted = findFriendshipOutput.findFriendshipRequest(user.getUsername(), friend.getUsername());

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
        User friend = findUserOutput.findByUsername(friendUsername);

        UsernameSenderAndUsernameReceiverDto usernameSenderAndUsernameReceiverDto = findFriendshipOutput.findFriendshipRequestByUsername(user, friend);

        var friendshipRequest = findFriendshipOutput.findFriendshipRequest(
                usernameSenderAndUsernameReceiverDto.getUsernameSender(),
                usernameSenderAndUsernameReceiverDto.getUsernameReceiver()
        );

        Boolean isAccepted = friendshipRequest.getAccepted();

        var friendshipRequests = findFriendshipOutput.findFriendshipRequestAcceptedFalse(
                usernameSenderAndUsernameReceiverDto.getUsernameSender(),
                usernameSenderAndUsernameReceiverDto.getUsernameReceiver(),
                isAccepted
        );

        validateFriendshipRequestExists(usernameSenderAndUsernameReceiverDto.getUsernameSender(), usernameSenderAndUsernameReceiverDto.getUsernameReceiver());
        validateDeleteIsSameUser(friendUsername, user);
        validateUserAlreadyFriend(friendUsername, user);

        friendsOutput.deleteFriendshipRequest(friendshipRequests.getId().getValue());
    }

    @Override
    public void deleteFriend(String friendUsername, String email) {
        User user = findUserOutput.findByEmail(email);
        User friend = findUserOutput.findByUsername(friendUsername);

        List<User> friends = friendsOutput.getAllFriendship(user);
        user.setFriends(friends);

        validateDeleteIsSameUser(friendUsername, user);
        validateExistsFriendship(friendUsername, user);
        deleteFriendshipRequest(friendUsername, email);

        friendsOutput.deleteFriend(user, friend);
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

    private void validateFriendshipRequestExists(String usernameSender, String usernameReceiver) {
        Boolean hasSentFriendRequest = findFriendshipOutput
                .hasSentFriendRequest(usernameSender, usernameReceiver);

        if (!hasSentFriendRequest) {
            throw new FriendshipRequestNotFoundException();
        }
    }

    private void validateFriendshipRequestAlreadyExistis(User user, User newFriend) {
        Boolean hasSentFriendRequest = findFriendshipOutput
                .hasSentFriendRequest(newFriend.getUsername(), user.getUsername());

        if (hasSentFriendRequest) {
            throw new FriendshipRequestAlreadyExistsException();
        }
    }

}

