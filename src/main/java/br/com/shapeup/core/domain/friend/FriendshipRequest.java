package br.com.shapeup.core.domain.friend;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class FriendshipRequest extends Entity<FriendshipRequestId> {
    private String usernameSender;
    private String usernameReceiver;
    private Boolean accepted = false;

    protected FriendshipRequest(FriendshipRequestId friendshipRequestId) {
        super(friendshipRequestId);
    }

    public FriendshipRequest(
            FriendshipRequestId friendshipRequestId,
            String usernameSender,
            String usernameReceiver,
            Boolean accepted
    ) {
        super(friendshipRequestId);
        this.usernameSender = usernameSender;
        this.usernameReceiver = usernameReceiver;
        this.accepted = accepted;
    }


    @Override
    public void validate(ValidationHandler handler) {
        new FriendshipRequestValidator(handler, this).validate();
    }

    public static FriendshipRequest create(
            String id,
            String usernameSender,
            String usernameReceiver,
            Boolean accepted
    ) {
        return new FriendshipRequest(
                FriendshipRequestId.from(id),
                usernameSender,
                usernameReceiver,
                accepted
        );
    }

    public String getUsernameSender() {
        return usernameSender;
    }

    public void setUsernameSender(String usernameSender) {
        this.usernameSender = usernameSender;
    }

    public String getUsernameReceiver() {
        return usernameReceiver;
    }

    public void setUsernameReceiver(String usernameReceiver) {
        this.usernameReceiver = usernameReceiver;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
