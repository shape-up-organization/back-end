package br.com.shapeup.adapters.output.integration.friend;

import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
import br.com.shapeup.adapters.output.repository.mapper.friend.FriendshipMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestEntity;
import br.com.shapeup.common.exceptions.friend.AlreadySentFriendRequestException;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
public class FindFriendshipAdapter implements FindFriendshipOutput {

    private final FriendshipMongoRepository friendshipMongoRepository;

    private final FriendshipMapper friendshipMapper;

    @Override
    public Boolean existsByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver) {
        Boolean alreadySentFriendRequest = friendshipMongoRepository
                .existsByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver);

        if (alreadySentFriendRequest) {
            log.error("The user {} already sent a friendship request to the user {}", usernameSender, usernameReceiver);
            throw new AlreadySentFriendRequestException(usernameSender, usernameReceiver);
        }

        return false;
    }

    @Override
    public FriendshipRequest findFriendshipRequest(String usernameSender, String usernameReceiver) {

        FriendshipRequestEntity friendshipRequestEntity = friendshipMongoRepository.
                findByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver);


        return friendshipMapper.friendshipRequestEntityToFriendshipRequest(friendshipRequestEntity);
    }
}
