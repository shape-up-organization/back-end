package br.com.shapeup.core.usecase;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;

public class UserPersistanceUsecase implements UserPersistanceInput {
    private final UserPersistanceOutput userPersistanceOutput;

    public UserPersistanceUsecase(UserPersistanceOutput userPersistanceOutput) {
        this.userPersistanceOutput = userPersistanceOutput;
    }

    @Override
    public void save(User user) {
        userPersistanceOutput.save(user);
    }

    @Override
    public void updatePassword(User user) {
        var password = user.getPassword();
        password.validatePassword();
        userPersistanceOutput.updatePassword(user);
    }

    @Override
    public void updateName(User user) {
        userPersistanceOutput.updateName(user);
    }

    @Override
    public void updateLastName(User user) {
        userPersistanceOutput.updateLastName(user);
    }

    @Override
    public void updateCellPhone(User user) {
        userPersistanceOutput.updateCellPhone(user);
    }

    @Override
    public void updateBirth(User user) {
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
}