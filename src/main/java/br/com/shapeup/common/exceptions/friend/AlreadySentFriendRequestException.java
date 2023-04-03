package br.com.shapeup.common.exceptions.friend;

public class AlreadySentFriendRequestException extends RuntimeException {
    public AlreadySentFriendRequestException(String usernameSender, String usernameReceiver) {
        super(String.format("Friendship request from %s to %s already sent", usernameSender, usernameReceiver));
    }
}
