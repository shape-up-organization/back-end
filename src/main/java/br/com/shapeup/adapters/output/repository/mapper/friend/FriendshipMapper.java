package br.com.shapeup.adapters.output.repository.mapper.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import org.springframework.stereotype.Component;

@Component
public interface FriendshipMapper {

    FriendshipRequest friendshipRequestDocumentToFriendshipRequest(FriendshipRequestDocument friendshipRequestDocument);

}

