package br.com.shapeup.core.usecase.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import java.util.List;

public class UserPersistanceUsecase implements UserPersistanceInput {
    private final UserPersistanceOutput userPersistanceOutput;

    public UserPersistanceUsecase(UserPersistanceOutput userPersistanceOutput) {
        this.userPersistanceOutput = userPersistanceOutput;
    }

    @Override
    public void deleteByEmail(String email) {
        userPersistanceOutput.deleteByEmail(email);
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        userPersistanceOutput.updateUser(email, userRequest);
    }

    @Override
    public User findUserByUsername(String username) {
        User searchUser = userPersistanceOutput.findUserByUsername(username);

        validateSearchUser(searchUser, username);

        return searchUser;
    }

    @Override
    public FriendshipStatus getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername) {
        return userPersistanceOutput.getFriendshipStatus(currentUserEmail, searchUserUsername);
    }

    @Override
    public List<User> findAllUserByFullName(String name, String lastName) {
        return userPersistanceOutput.findAllUserByFullName(name, lastName);
    }


    private void validateSearchUser(User searchUser, String username) {
        if (searchUser == null) {
            throw new UserNotFoundException(username);
        }
    }
}