package br.com.shapeup.core.usecase;

import br.com.shapeup.core.domain.User;
import br.com.shapeup.core.port.input.UserPersistanceInput;
import br.com.shapeup.core.port.output.UserPersistanceOutput;

public class UserPersistanceUsecase implements UserPersistanceInput {
    private final UserPersistanceOutput userPersistanceOutput;

    public UserPersistanceUsecase(UserPersistanceOutput userPersistanceOutput) {
        this.userPersistanceOutput = userPersistanceOutput;
    }

    @Override
    public void save(User user){
        userPersistanceOutput.save(user);

    }
}
