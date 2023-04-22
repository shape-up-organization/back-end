package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private final UserJpaRepository UserJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FindFriendshipOutput findFriendshipOutput;
    private final FindUserOutput findUserOutput;
    private final FriendshipJpaRepository friendshipJpaRepository;

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        UserEntity userEntity = UserJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        userEntity.setActive(false);

        UserJpaRepository.save(userEntity);
    }

    @Override
    public User findUser(String email) {
        UserEntity userEntity = UserJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        User user = userMapper.userEntitytoUser(userEntity);

        return user;
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        UserEntity userEntity = UserJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        if (userRequest.getCellPhone() != null) {
            CellPhone.validateCellPhone(userRequest.getCellPhone());
            userEntity.setCellPhone(userRequest.getCellPhone());
        }

        if (userRequest.getBirth() != null) {
            var birth = Birth.convertBirth(userRequest.getBirth());

            Birth.validateBirth(birth);
            userEntity.setBirth(birth);
        }

        if (userRequest.getBiography() != null) {
            userEntity.setBiography(userRequest.getBiography());
        }

        if (userRequest.getName() != null) {
            userEntity.setName(userRequest.getName());
        }

        if (userRequest.getLastName() != null) {
            userEntity.setLastName(userRequest.getLastName());
        }

        if (userRequest.getUsername() != null) {
            userEntity.setUsername(userRequest.getUsername());
        }

        if (userRequest.getPassword() != null) {
            Password.validatePassword(userRequest.getPassword());

            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
            userEntity.setPassword(encodedPassword);
        }

        UserJpaRepository.save(userEntity);
    }

    @Override
    public User findUserByUsername(String username) {
        UserEntity userEntity = UserJpaRepository.findByUsername(username).orElseThrow(() -> {
            log.error("[USER PERSISTENCE ADAPTER] - User not found by username: {}", username);
            throw new UserNotFoundException(username);
        });

        var user = userMapper.userEntitytoUser(userEntity);
        log.debug("[USER PERSISTENCE ADAPTER] - User found by username: {}", username);

        return user;
    }

    public FriendshipStatus getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername) {
        var currentUser = findUserOutput.findByEmail(currentUserEmail);
        var searchUser = findUserOutput.findByUsername(searchUserUsername.get(0));

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
    public List<User> findAllUserByFullName(String name, String lastName) {
        List<UserEntity> userEntities = UserJpaRepository.findAllByNameIgnoreCaseAndLastNameIgnoreCase(name, lastName);

        return userMapper.userEntityListToUserList(userEntities);
    }
}
