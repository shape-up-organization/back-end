package br.com.shapeup.adapters.output.repository.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User userEntitytoUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);

    UserEntity userRegisterRequestToUserEntity(UserAuthRegisterRequest userAuthRegisterRequest);

    List<User> friendsEntityToUserFriendsList(List<FriendsEntity> friends);

    List<User> userEntityListToUserList(List<UserEntity> userEntityList);

    List<UserEntity> userListToUserEntityList(List<User> users);
}
