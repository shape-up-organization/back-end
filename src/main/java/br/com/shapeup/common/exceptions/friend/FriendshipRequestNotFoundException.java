package br.com.shapeup.common.exceptions.friend;

public class FriendshipRequestNotFoundException extends RuntimeException {

    public FriendshipRequestNotFoundException(String message) {
        super(message);
    }
    public FriendshipRequestNotFoundException() {
        super("Friendship request not found");
    }
}
