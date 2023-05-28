package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.ShapeUpBaseException;
import br.com.shapeup.common.exceptions.user.InvalidCredentialException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.common.utils.ObjectUtils;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final FindFriendshipOutput findFriendshipOutput;
    private final FindUserOutput findUserOutput;
    private final FriendshipJpaRepository friendshipJpaRepository;

    @Override
    public void deleteById(User user) {
        userJpaRepository.deleteById(UUID.fromString(user.getId().getValue()));
    }

    @Override
    public User findUser(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        User user = userMapper.userEntitytoUser(userEntity);

        return user;
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new InvalidCredentialException();
        });

        Try.run(() -> ObjectUtils.copyNonNullProperties(userEntity, userRequest))
                .onFailure(e -> {
                    log.error("[USER PERSISTENCE ADAPTER] - Error on copy properties: {}", e.getMessage());
                    throw new ShapeUpBaseException(e.getMessage(), e.getCause());
                });

        userJpaRepository.save(userEntity);
    }

    @Override
    public User findUserByUsername(String username) {

        return Try.of(() -> userJpaRepository.findByUsername(username))
                .onSuccess(userEntity -> log.info("[USER PERSISTENCE ADAPTER] - User found by username: {}", username))
                .onFailure(throwable -> log.error("[USER PERSISTENCE ADAPTER] - User not found by username: {}", username, throwable))
                .map(userEntity -> userEntity.orElseThrow(() -> new UserNotFoundException(username)))
                .map(userMapper::userEntitytoUser)
                .get();
    }

    @Override
    public List<User> findAllUserByFullName(String fullName) {
        List<UserEntity> userEntities = userJpaRepository.findByFullNameContainsIgnoreCase(fullName);

        return userMapper.userEntityListToUserList(userEntities);
    }

    @Override
    public FriendshipStatus getFriendshipStatusForUser(String currentUserEmail, String searchUserUsername) {
        User currentUser = findUserOutput.findByEmail(currentUserEmail);
        User searchUser = findUserOutput.findByUsername(searchUserUsername);
        var usernameSenderAndUsernameReceiver = findFriendshipOutput.findFriendshipRequestByUsername(currentUser, searchUser);

        boolean haveFriendRequest = findFriendshipOutput.hasSentFriendRequest(usernameSenderAndUsernameReceiver.getUsernameSender(), usernameSenderAndUsernameReceiver.getUsernameReceiver());
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);

        var friends = friendshipJpaRepository.findAllByUserReceiver(currentUserEntity);

        boolean isFriend = friends.stream()
                .anyMatch(friend -> friend.getUserSender()
                        .getUsername()
                        .equals(searchUser.getUsername()));

        return new FriendshipStatus(haveFriendRequest, isFriend, usernameSenderAndUsernameReceiver.getUsernameSender());
    }

    @Override
    public List<FriendshipStatus> getFriendshipStatusForUser(String currentUserEmail, List<String> searchUserUsername) {
        var currentUser = findUserOutput.findByEmail(currentUserEmail);
        List<FriendshipStatus> friendshipStatuses = new ArrayList<>();

        for (String username : searchUserUsername) {
            friendshipStatuses.add(getFriendshipStatusForUser(currentUserEmail, username));
        }

        return friendshipStatuses;
    }

    @Override
    public List<User> findAllUserByUsername(String username) {
        return Try.of(() -> userJpaRepository.findAllByUsernameStartingWithIgnoreCase(username))
                .map(userMapper::userEntityListToUserList)
                .onSuccess(users -> log.info("[USER PERSISTENCE ADAPTER] - Users found by username: {}", username))
                .onFailure(throwable -> {
                    log.error("[USER PERSISTENCE ADAPTER] - Users not found by username: {}", username);
                    throw new ShapeUpBaseException(throwable.getMessage(), throwable.getCause());
                }).get();
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        userJpaRepository.save(userEntity);
    }

    @Override
    public void saveAll(List<User> users) {
        List<UserEntity> userEntities = userMapper.userListToUserEntityList(users);

        userJpaRepository.saveAll(userEntities);
    }

    @Override
    public Long getUserXp(String username) {
        return userJpaRepository.findXpByUsername(username);
    }
}
