package br.com.shapeup.adapters.output.repository.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userEntitytoUser(UserEntity userEntity) {
        return User.newUser(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                Email.create(userEntity.getEmail()),
                CellPhone.create(userEntity.getCellPhone()),
                Password.create(userEntity.getPassword()),
                Birth.create(userEntity.getBirth()),
                userEntity.getBiography(),
                userEntity.getXp(),
                userEntity.getProfilePicture()
        );
    }

    @Override
    public UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .id(UUID.fromString(user.getId().getValue()))
                .name(user.getFullName().getName())
                .lastName(user.getFullName().getLastName())
                .username(user.getUsername())
                .email(user.getEmail().getValue())
                .cellPhone(user.getCellPhone().getValue())
                .password(user.getPassword().getValue())
                .birth(user.getBirth().getValue())
                .build();
    }

    @Override
    public UserEntity userRegisterRequestToUserEntity(UserAuthRegisterRequest userAuthRegisterRequest) {
        return UserEntity.builder()
                .name(userAuthRegisterRequest.getName())
                .lastName(userAuthRegisterRequest.getLastName())
                .username(userAuthRegisterRequest.getUsername())
                .email(userAuthRegisterRequest.getEmail())
                .cellPhone(userAuthRegisterRequest.getCellPhone())
                .password(userAuthRegisterRequest.getPassword())
                .birth(LocalDate.parse(userAuthRegisterRequest.getBirth(), Birth.DATE_FORMATTER))
                .fullName(userAuthRegisterRequest.getName() + " " + userAuthRegisterRequest.getLastName())
                .build();
    }

    @Override
    public List<User> friendsEntityToUserFriendsList(List<FriendsEntity> friends) {
        return List.of(
                friends.stream().map(user -> userEntitytoUser(user.getUserReceiver())).toArray(User[]::new)
        );
    }

    @Override
    public List<User> userEntityListToUserList(List<UserEntity> userEntityList) {
        return List.of(
                userEntityList.stream().map(this::userEntitytoUser).toArray(User[]::new)
        );
    }
}
