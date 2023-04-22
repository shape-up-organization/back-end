package br.com.shapeup.common.exceptions.friend;

public class DeleteYourselfAsAFriendException extends RuntimeException{
    public DeleteYourselfAsAFriendException() {
        super("You can't delete yourself as a friend");
    }
}
