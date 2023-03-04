package br.com.shapeup.core.ports.input;

import br.com.shapeup.core.domain.user.User;

public interface UserPersistanceInput {
    void save(User user);

    void updatePassword(User user);
}
