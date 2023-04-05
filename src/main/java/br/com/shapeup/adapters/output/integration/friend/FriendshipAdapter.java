package br.com.shapeup.adapters.output.integration.friend;

import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.friend.FriendshipRequestId;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipAdapter implements FriendshipOutput {

    private final FriendshipMongoRepository friendsMongoRepository;
    private final FriendshipJpaRepository friendsJpaRepository;
    private final UserMapper userMapper;

    @Override
    public FriendshipRequest sendFriendRequest(User currentUser, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);

        var friendRequestEntity = new FriendshipRequestEntity(
                currentUserEntity.getUsername(),
                newFriendEntity.getUsername()
        );

        saveFriendRequestToMongo(friendRequestEntity);

        return createFriendshipRequestFromEntity(friendRequestEntity);
    }

    @Override
    public FriendshipRequest acceptFriendRequest(User currentUser, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);

        FriendshipRequestEntity friendshipRequestEntity = createAcceptedFriendshipRequest(
                currentUserEntity,
                newFriendEntity
        );

        FriendsEntity friendEntityUpdated = createFriendEntityFromRequest(
                currentUserEntity,
                newFriendEntity,
                friendshipRequestEntity
        );

        FriendshipRequest friendshipRequestResponse = createFriendshipRequestResponse(
                currentUserEntity,
                newFriendEntity,
                friendshipRequestEntity
        );

        saveFriendshipRequest(friendshipRequestEntity, friendEntityUpdated);

        return friendshipRequestResponse;
    }

    private static FriendshipRequest createFriendshipRequestFromEntity(FriendshipRequestEntity friendRequestEntity) {
        return FriendshipRequest.create(
                friendRequestEntity.getId(),
                friendRequestEntity.getUsernameSender(),
                friendRequestEntity.getUsernameReceiver(),
                false
        );
    }

    private void saveFriendRequestToMongo(FriendshipRequestEntity friendRequestEntity) {
        try {
            friendsMongoRepository.save(friendRequestEntity);
        } catch (Exception e) {
            log.error("Error while saving friend request: cause: {}", e.getMessage());
            throw new RuntimeException(String.format("Error while saving friend request: %s", e.getCause()));
        }
    }

    private static FriendshipRequest createFriendshipRequestResponse(UserEntity currentUserEntity, UserEntity newFriendEntity, FriendshipRequestEntity friendshipRequestEntity) {
        return new FriendshipRequest(
                FriendshipRequestId.from(friendshipRequestEntity.getId()),
                currentUserEntity.getId().toString(),
                newFriendEntity.getId().toString(),
                true
        );
    }

    private void saveFriendshipRequest(FriendshipRequestEntity friendshipRequestEntity, FriendsEntity friendEntityUpdated) {
        try {
            friendsJpaRepository.save(friendEntityUpdated);
            friendsMongoRepository.save(friendshipRequestEntity);
        } catch (Exception e) {
            log.error("Error while saving friend request: cause: {}", e.getMessage());
            throw new RuntimeException(String.format("Error while saving friend request: %s", e.getCause()));
        }
    }

    private static FriendshipRequestEntity createAcceptedFriendshipRequest(UserEntity currentUserEntity, UserEntity newFriendEntity) {
        var friendshipRequestEntity = new FriendshipRequestEntity(currentUserEntity.getUsername(), newFriendEntity.getUsername());
        friendshipRequestEntity.setAccepted(true);
        friendshipRequestEntity.setUpdatedAt(LocalDate.now());
        return friendshipRequestEntity;
    }

    private static FriendsEntity createFriendEntityFromRequest(
            UserEntity currentUserEntity,
            UserEntity newFriendEntity,
            FriendshipRequestEntity friendshipRequestEntity
    ) {
        return FriendsEntity.builder()
                .userReceiver(newFriendEntity)
                .userSender(currentUserEntity)
                .accepted(true)
                .sentAt(friendshipRequestEntity.getCreatedAt())
                .acceptedAt(LocalDate.now())
                .build();
    }
}
