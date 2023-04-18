package br.com.shapeup.adapters.output.integration.friend;

import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.friend.FriendshipRequestId;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipAdapter implements FriendshipOutput {

    private final FriendshipMongoRepository friendsMongoRepository;
    private final FriendshipJpaRepository friendshipJpaRepository;
    private final UserMapper userMapper;

    @Override
    public FriendshipRequest sendFriendRequest(User currentUser, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);

        var friendRequestEntity = new FriendshipRequestDocument(
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

        FriendshipRequestDocument friendshipRequestDocument = createAcceptedFriendshipRequest(
                currentUserEntity,
                newFriendEntity
        );

        FriendsEntity friendEntityUpdated = createFriendEntityFromRequest(
                currentUserEntity,
                newFriendEntity,
                friendshipRequestDocument
        );

        FriendshipRequest friendshipRequestResponse = createFriendshipRequestResponse(
                currentUserEntity,
                newFriendEntity,
                friendshipRequestDocument
        );

        saveFriendshipRequest(friendshipRequestDocument, friendEntityUpdated);

        return friendshipRequestResponse;
    }

    @Override
    public List<User> getAllFriendship(User currentUser) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);
        List<FriendsEntity> friendsEntityList = friendshipJpaRepository.findAllByUserReceiver(currentUserEntity);

        List<UserEntity> friends = getSenderUsersFromFriendEntities(friendsEntityList);

        return userMapper.userEntityListToUserList(friends);

    }

    private static FriendshipRequest createFriendshipRequestFromEntity(FriendshipRequestDocument friendRequestEntity) {
        return FriendshipRequest.create(
                friendRequestEntity.getId(),
                friendRequestEntity.getUsernameSender(),
                friendRequestEntity.getUsernameReceiver(),
                false
        );
    }

    private void saveFriendRequestToMongo(FriendshipRequestDocument friendRequestEntity) {
        try {
            friendsMongoRepository.save(friendRequestEntity);
        } catch (Exception e) {
            log.error("Error while saving friend request: cause: {}", e.getMessage());
            throw new RuntimeException(String.format("Error while saving friend request: %s", e.getCause()));
        }
    }

    private static FriendshipRequest createFriendshipRequestResponse(UserEntity currentUserEntity, UserEntity newFriendEntity, FriendshipRequestDocument friendshipRequestDocument) {
        return new FriendshipRequest(
                FriendshipRequestId.from(friendshipRequestDocument.getId()),
                currentUserEntity.getId().toString(),
                newFriendEntity.getId().toString(),
                true
        );
    }

    private void saveFriendshipRequest(FriendshipRequestDocument friendshipRequestDocument, FriendsEntity friendEntityReceiverUpdated) {
        try {
            friendshipJpaRepository.save(friendEntityReceiverUpdated);

            FriendsEntity friendEntitySenderUpdated = createFriendEntityFromRequest(
                    friendEntityReceiverUpdated.getUserReceiver(),
                    friendEntityReceiverUpdated.getUserSender(),

                    FriendshipRequestDocument.builder()
                            .id(friendshipRequestDocument.getId())
                            .usernameSender(friendshipRequestDocument.getUsernameSender())
                            .usernameReceiver(friendshipRequestDocument.getUsernameReceiver())
                            .createdAt(friendshipRequestDocument.getCreatedAt())
                            .updatedAt(friendshipRequestDocument.getUpdatedAt())
                            .accepted(true)
                            .build()
            );

            friendshipJpaRepository.save(friendEntitySenderUpdated);
            friendsMongoRepository.save(friendshipRequestDocument);
        } catch (Exception e) {
            log.error("Error while saving friend request: cause: {}", e.getMessage());
            throw new RuntimeException(String.format("Error while saving friend request: %s", e.getCause()));
        }
    }

    private static FriendshipRequestDocument createAcceptedFriendshipRequest(UserEntity currentUserEntity, UserEntity newFriendEntity) {
        var friendshipRequestDocument = new FriendshipRequestDocument(currentUserEntity.getUsername(), newFriendEntity.getUsername());
        friendshipRequestDocument.setAccepted(true);
        friendshipRequestDocument.setUpdatedAt(LocalDate.now());
        return friendshipRequestDocument;
    }

    private static FriendsEntity createFriendEntityFromRequest(
            UserEntity currentUserEntity,
            UserEntity newFriendEntity,
            FriendshipRequestDocument friendshipRequestDocument
    ) {
        return FriendsEntity.builder()
                .userReceiver(newFriendEntity)
                .userSender(currentUserEntity)
                .accepted(true)
                .sentAt(friendshipRequestDocument.getCreatedAt())
                .acceptedAt(LocalDate.now())
                .build();
    }

    private static List<UserEntity> getSenderUsersFromFriendEntities(List<FriendsEntity> friendsEntityList) {
        return friendsEntityList.stream()
                .map(FriendsEntity::getUserSender).toList();
    }

    @Override
    public void deleteFriendshipRequest(User user, User newFriend) {
        friendsMongoRepository.deleteByUsernameSenderAndUsernameReceiver(user.getUsername(), newFriend.getUsername());
    }
    @Override
    public void deleteFriend(User user, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(user);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);
        friendshipJpaRepository.deleteByUserReceiverIdAndUserSenderId(currentUserEntity.getId(),newFriendEntity.getId());
    }
}
