package br.com.shapeup.common.exceptions.friend;

public class DuplicateFriendshipException extends RuntimeException {
    public DuplicateFriendshipException(String username) {
        super(String.format("User %s is already your friend", username));
    }
}
