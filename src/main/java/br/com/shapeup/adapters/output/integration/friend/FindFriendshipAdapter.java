package br.com.shapeup.adapters.output.integration.friend;

import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
import br.com.shapeup.adapters.output.repository.mapper.friend.FriendshipMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.common.domain.dto.UsernameSenderAndUsernameReceiverDto;
import br.com.shapeup.common.exceptions.friend.AlreadySentFriendRequestException;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import java.util.List;
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
    public void hasNotSentFriendRequestYet(String usernameSender, String usernameReceiver) {
        Boolean alreadySentFriendRequest = friendshipMongoRepository
                .findByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver)
                .isPresent();

        if (alreadySentFriendRequest) {
            log.error("The user {} already sent a friendship request to the user {}", usernameSender, usernameReceiver);
            throw new AlreadySentFriendRequestException(usernameSender, usernameReceiver);
        }

    }

    @Override
    public FriendshipRequest findFriendshipRequest(String usernameSender, String usernameReceiver) {

        FriendshipRequestDocument friendshipRequestDocument = friendshipMongoRepository.
                findByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver).get();

        return friendshipMapper.friendshipRequestDocumentToFriendshipRequest(friendshipRequestDocument);
    }

    @Override
    public Boolean hasSentFriendRequest(String usernameSender, String usernameReceiver) {

        return friendshipMongoRepository.findByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver)
                .isPresent();
    }

    @Override
    public List<FriendshipRequest> findAllFriendshipRequest(String usernameSender, String usernameReceiver) {
        return friendshipMongoRepository.findAllByUsernameSenderAndUsernameReceiver(usernameSender, usernameReceiver).stream()
                .map(friendshipMapper::friendshipRequestDocumentToFriendshipRequest).toList();
    }

    @Override
    public FriendshipRequest findFriendshipRequestAcceptedFalse(String usernameSender, String usernameReceiver, Boolean accepted) {
        FriendshipRequestDocument friendshipRequestDocument = friendshipMongoRepository
                .findByUsernameSenderAndUsernameReceiverAndAccepted(usernameSender, usernameReceiver, accepted).get();

        return friendshipMapper.friendshipRequestDocumentToFriendshipRequest(friendshipRequestDocument);
    }

    @Override
    public UsernameSenderAndUsernameReceiverDto findFriendshipRequestByUsername(User currentUser, User searchUser) {
        List<FriendshipRequestDocument> friendshipRequests = friendshipMongoRepository.findAllByUsernameSenderOrUsernameReceiverEqualsIgnoreCase(currentUser.getUsername());
        var usernameSenderAndUsernameReceiverDto = new UsernameSenderAndUsernameReceiverDto();

        for (FriendshipRequestDocument friendshipRequest : friendshipRequests) {

            if (friendshipRequest.getUsernameSender().equals(currentUser.getUsername())) {

                if (friendshipRequest.getUsernameReceiver().equals(searchUser.getUsername())) {
                    return usernameSenderAndUsernameReceiverDto.builder()
                            .usernameSender(friendshipRequest.getUsernameSender())
                            .usernameReceiver(friendshipRequest.getUsernameReceiver())
                            .build();
                }
            } else if (friendshipRequest.getUsernameReceiver().equals(currentUser.getUsername())) {

                if (friendshipRequest.getUsernameSender().equals(searchUser.getUsername())) {
                    return usernameSenderAndUsernameReceiverDto.builder()
                            .usernameSender(friendshipRequest.getUsernameSender())
                            .usernameReceiver(friendshipRequest.getUsernameReceiver())
                            .build();
                }
            }
        }

        return usernameSenderAndUsernameReceiverDto;
    }
}
