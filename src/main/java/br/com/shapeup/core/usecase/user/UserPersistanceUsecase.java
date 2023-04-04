package br.com.shapeup.core.usecase;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import java.net.URL;

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
    public URL uploadPicture(Object file, String token) {
        return userPersistanceOutput.uploadPicture(file, token);
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        userPersistanceOutput.updateUser(email, userRequest);
    }
}