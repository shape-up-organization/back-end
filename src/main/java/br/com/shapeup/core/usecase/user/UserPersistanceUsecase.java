package br.com.shapeup.core.usecase.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;

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
}