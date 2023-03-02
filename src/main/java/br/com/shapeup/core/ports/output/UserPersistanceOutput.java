package br.com.shapeup.core.ports.output;

import br.com.shapeup.core.domain.User;

public interface UserPersistanceOutput {
  void save(User user);
  void updatePassword(User user);
}
