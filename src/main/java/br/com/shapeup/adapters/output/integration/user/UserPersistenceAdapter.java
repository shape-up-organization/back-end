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
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FindFriendshipOutput findFriendshipOutput;
    private final FindUserOutput findUserOutput;
    private final FriendshipJpaRepository friendshipJpaRepository;

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        userEntity.setActive(false);

        userJpaRepository.save(userEntity);
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
        UserEntity userEntity = userJpaRepository.findByUsername(username).orElseThrow(() -> {
            log.error("[USER PERSISTENCE ADAPTER] - User not found by username: {}", username);
            throw new UserNotFoundException(username);
        });

        var user = userMapper.userEntitytoUser(userEntity);
        log.debug("[USER PERSISTENCE ADAPTER] - User found by username: {}", username);

        return user;
    }

    @Override
    public List<User> findAllUserByFullName(String name, String lastName) {
        List<UserEntity> userEntities = userJpaRepository.findAllByNameIgnoreCaseAndLastNameIgnoreCase(name, lastName);

        return userMapper.userEntityListToUserList(userEntities);
    }

    @Override
    public FriendshipStatus getFriendshipStatus(String currentUserEmail, String searchUserUsername) {
        var currentUser = findUserOutput.findByEmail(currentUserEmail);
        var searchUser = findUserOutput.findByUsername(searchUserUsername);

        boolean haveFriendRequest = findFriendshipOutput.hasSentFriendRequest(currentUser.getUsername(), searchUser.getUsername());
        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);

        var friends = friendshipJpaRepository.findAllByUserReceiver(currentUserEntity);

        boolean isFriend = friends.stream()
                .anyMatch(friend -> friend.getUserSender()
                        .getUsername()
                        .equals(searchUser.getUsername()));

        return new FriendshipStatus(haveFriendRequest, isFriend);
    }

    @Override
    public List<FriendshipStatus> getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername) {
        var currentUser = findUserOutput.findByEmail(currentUserEmail);
        List<FriendshipStatus> friendshipStatuses = new ArrayList<>();

        for (String username : searchUserUsername) {
            var searchUser = findUserOutput.findByUsername(username);

            Boolean haveFriendRequest = findFriendshipOutput.hasSentFriendRequest(currentUser.getUsername(), searchUser.getUsername());
            UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);

            var friends = friendshipJpaRepository.findAllByUserReceiver(currentUserEntity);

            Boolean isFriend = friends.stream()
                    .anyMatch(friend -> friend.getUserSender()
                            .getUsername()
                            .equals(searchUser.getUsername()));

            friendshipStatuses.add(new FriendshipStatus(haveFriendRequest, isFriend));
        }

        return friendshipStatuses;
    }
}
