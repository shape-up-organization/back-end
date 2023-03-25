package br.com.shapeup.core.usecase.user;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import java.net.URL;

public class UserPersistanceUsecase implements UserPersistanceInput {
    private final UserPersistanceOutput userPersistanceOutput;

    public UserPersistanceUsecase(UserPersistanceOutput userPersistanceOutput) {
        this.userPersistanceOutput = userPersistanceOutput;
    }

    @Override
    public void updatePassword(User user) {
        user.getPassword().validatePassword();
        userPersistanceOutput.updatePassword(user);
    }

    @Override
    public void updateName(User user) {
        user.validateName();
        userPersistanceOutput.updateName(user);
    }

    @Override
    public void updateLastName(User user) {
        user.validateLastName();
        userPersistanceOutput.updateLastName(user);
    }

    @Override
    public void updateCellPhone(User user) {
        user.getCellPhone().validateCellPhone();
        userPersistanceOutput.updateCellPhone(user);
    }

    @Override
    public void updateBirth(User user) {
        user.getBirth().validateBirth();
        userPersistanceOutput.updateBirth(user);
    }

    @Override
    public void updateBiography(User user) {
        userPersistanceOutput.updateBiography(user);
    }

    @Override
    public void deleteByEmail(String email) {
        userPersistanceOutput.deleteByEmail(email);
    }

    @Override
    public URL uploadPicture(Object file, String token) {
        return userPersistanceOutput.uploadPicture(file, token);
    }
}