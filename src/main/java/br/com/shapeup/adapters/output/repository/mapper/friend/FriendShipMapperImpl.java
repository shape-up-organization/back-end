package br.com.shapeup.adapters.output.repository.mapper.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.friend.FriendshipRequestId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FriendShipMapperImpl implements FriendshipMapper {

    @Override
    public FriendshipRequest friendshipRequestDocumentToFriendshipRequest(FriendshipRequestDocument friendshipRequestDocument) {
        if (friendshipRequestDocument == null) {
            return null;
        }

        return new FriendshipRequest(
                FriendshipRequestId.from(friendshipRequestDocument.getId()),
                friendshipRequestDocument.getUsernameSender(),
                friendshipRequestDocument.getUsernameReceiver(),
                friendshipRequestDocument.getAccepted()
        );
    }
}
