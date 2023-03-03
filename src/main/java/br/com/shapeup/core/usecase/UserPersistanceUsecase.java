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
    userPersistanceOutput.updatePassword(user);
  }
}
