package br.com.shapeup.core.ports.output.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface UserPersistanceOutput {

    void deleteByEmail(String email);

    User findUser(String email);

    void updateUser(String email, UserRequest userRequest);

    User findUserByUsername(String username);

    List<User> findAllUserByFullName(String name, String lastName);


    FriendshipStatus getFriendshipStatus(String currentUserEmail, String searchUserUsername);

    List<FriendshipStatus> getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername);

}
