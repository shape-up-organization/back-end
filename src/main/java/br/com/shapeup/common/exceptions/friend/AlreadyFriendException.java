package br.com.shapeup.common.exceptions.friend;

public class AlreadyFriendException extends RuntimeException {
    public AlreadyFriendException(String username) {
        super(String.format("User %s is already your friend", username));
    }
}
