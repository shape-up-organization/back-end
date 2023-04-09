package br.com.shapeup.core.domain.friend;

import br.com.shapeup.common.exceptions.user.InvalidUsernameException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class FriendshipRequestValidator extends Validator {

    private final FriendshipRequest friendshipRequest;

    protected FriendshipRequestValidator(
            ValidationHandler anHandler,
            FriendshipRequest friendshipRequest
    ) {
        super(anHandler);
        this.friendshipRequest = friendshipRequest;
    }

    @Override
    public void validate() {
        validateUsernameReceiver();
    }

    public void validateUsernameReceiver() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        String usernameUserReceiver = this.friendshipRequest.getUsernameReceiver();
        if (usernameUserReceiver == null || usernameUserReceiver.isBlank()) {
            throw new InvalidUsernameException("'User Receiver' name should not be null");
        }

        String usernameUserSender = this.friendshipRequest.getUsernameSender();
        if (usernameUserSender == null || usernameUserSender.isBlank()) {
            throw new InvalidUsernameException("'User Sender' name should not be null");
        }
    }
}
