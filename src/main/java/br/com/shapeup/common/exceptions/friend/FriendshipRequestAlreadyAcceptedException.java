package br.com.shapeup.common.exceptions.friend;

public class FriendshipRequestAlreadyAcceptedException extends RuntimeException {

    public FriendshipRequestAlreadyAcceptedException() {
        super("Friendship request already accepted");
    }
}
