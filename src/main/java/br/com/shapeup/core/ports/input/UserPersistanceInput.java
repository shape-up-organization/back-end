package br.com.shapeup.core.ports.input;

import br.com.shapeup.core.domain.User;

public interface UserPersistanceInput {
  void save(User user);
  void updatePassword(User user);
}
