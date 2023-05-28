package br.com.shapeup.core.ports.input.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface UserPersistanceInput {

    void deleteByEmail(String email);

    void updateUser(String email, UserRequest userRequest);

    User findUserByUsername(String username);

    List<FriendshipStatus> getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername);

    List<User> findAllUserByFullName(String fullName);

    List<User> findAllUserByUsername(String username);

    Long getUserXp(String username);
}
