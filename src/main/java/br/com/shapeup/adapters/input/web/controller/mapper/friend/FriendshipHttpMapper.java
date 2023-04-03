package br.com.shapeup.adapters.input.web.controller.mapper.friend;


import br.com.shapeup.adapters.input.web.controller.response.friend.AcceptedFriendshipResponse;
import br.com.shapeup.adapters.input.web.controller.response.friend.RequestFriendshipResponse;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.friend.FriendshipRequestId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {FriendshipHttpMapper.class})
@Component
public interface FriendshipHttpMapper {
    FriendshipHttpMapper INSTANCE = Mappers.getMapper(FriendshipHttpMapper.class);

    RequestFriendshipResponse friendRequestToRequestFriendshipResponse(FriendshipRequest friendshipRequest);

    AcceptedFriendshipResponse friendRequestToAcceptedFriendshipResponse(FriendshipRequest friendshipRequest);

}
