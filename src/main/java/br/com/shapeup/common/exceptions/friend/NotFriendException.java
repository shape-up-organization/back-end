package br.com.shapeup.common.exceptions.friend;


public class NotFriendException extends RuntimeException {
    public NotFriendException(String userNotFriend) {
        super(String.format("User %s is not your friend", userNotFriend));
    }
}

