package br.com.shapeup.adapters.output.repository.mapper.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring", uses = {FriendshipMapper.class})
@Component
public interface FriendshipMapper {

    FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

    FriendshipRequest friendshipRequestDocumentToFriendshipRequest(FriendshipRequestDocument friendshipRequestDocument);

}

