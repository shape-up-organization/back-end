package br.com.shapeup.common.exceptions.friend;

public class FriendshipRequestAlreadyExistsException extends RuntimeException {
public FriendshipRequestAlreadyExistsException() {

        super("Friendship request already exists");
    }

    public FriendshipRequestAlreadyExistsException(String message) {
        super(message);
    }
}
