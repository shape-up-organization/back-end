package br.com.shapeup.common.exceptions.friend;

public class AddYourselfAsAFriendException extends RuntimeException {
    public AddYourselfAsAFriendException() {
        super("You can't add yourself as a friend");
    }
}
