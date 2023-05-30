package br.com.shapeup.adapters.output.integration.friend;

import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.domain.enums.UserActionEnum;
import br.com.shapeup.core.domain.friend.FriendshipRequest;
import br.com.shapeup.core.domain.friend.FriendshipRequestId;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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
    private final UserJpaRepository userJpaRepository;

    @Override
    public FriendshipRequest sendFriendRequest(User currentUser, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);

        var friendRequestEntity = new FriendshipRequestDocument(
                currentUserEntity.getUsername(),
                newFriendEntity.getUsername()
        );

        saveFriendRequestToMongo(friendRequestEntity);

        currentUserEntity.setXp(currentUser.getXp() + UserActionEnum.SENDFRIENDSHIPREQUEST.getXp());
        userJpaRepository.save(currentUserEntity);

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

        newFriendEntity.setXp(newFriendEntity.getXp() + UserActionEnum.ACCEPTFRIENDSHIPREQUEST.getXp());
        userJpaRepository.save(newFriendEntity);

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

    private static FriendshipRequest createFriendshipRequestResponse(
            UserEntity currentUserEntity,
            UserEntity newFriendEntity,
            FriendshipRequestDocument friendshipRequestDocument
    ) {
        return new FriendshipRequest(
                FriendshipRequestId.from(friendshipRequestDocument.getId()),
                currentUserEntity.getId().toString(),
                newFriendEntity.getId().toString(),
                true
        );
    }

    private void saveFriendshipRequest(
            FriendshipRequestDocument friendshipRequestDocument,
            FriendsEntity friendEntityReceiverUpdated
    ) {
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

            friendsMongoRepository.findByUsernameSenderAndUsernameReceiver(
                    friendshipRequestDocument.getUsernameSender(),
                    friendshipRequestDocument.getUsernameReceiver()
            ).ifPresent(friendsMongoRepository::delete);

            friendsMongoRepository.save(friendshipRequestDocument);
        } catch (Exception e) {
            log.error("Error while saving friend request: cause: {}", e.getMessage());
            throw new RuntimeException(String.format("Error while saving friend request: %s", e.getCause()));
        }
    }

    private static FriendshipRequestDocument createAcceptedFriendshipRequest(
            UserEntity currentUserEntity,
            UserEntity newFriendEntity
    ) {
        var friendshipRequestDocument = new FriendshipRequestDocument(
                currentUserEntity.getUsername(),
                newFriendEntity.getUsername()
        );
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
    public void deleteFriendshipRequest(String friendshipRequestId) {
        friendsMongoRepository.deleteById(friendshipRequestId);
    }

    @Override
    @Transactional
    public void deleteFriend(User user, User newFriend) {
        UserEntity currentUserEntity = userMapper.userToUserEntity(user);
        UserEntity newFriendEntity = userMapper.userToUserEntity(newFriend);
        friendshipJpaRepository.deleteByUserReceiverIdAndUserSenderId(
                currentUserEntity.getId(),
                newFriendEntity.getId()
        );
        friendshipJpaRepository.deleteByUserReceiverIdAndUserSenderId(
                newFriendEntity.getId(),
                currentUserEntity.getId()
        );
    }

    @Override
    @Transactional
    public void deleteAllFriendshipByUserId(User user) {
        friendsMongoRepository.deleteAllByUsernameSenderOrUsernameReceiverEqualsIgnoreCase(user.getUsername(), user.getUsername());

        friendshipJpaRepository.deleteAllByUserReceiverIdOrUserSenderId(
                UUID.fromString(user.getId().getValue()),
                UUID.fromString(user.getId().getValue())
        );
    }
}

