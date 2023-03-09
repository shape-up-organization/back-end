package br.com.shapeup.core.ports.input;

import br.com.shapeup.core.domain.user.User;

import java.util.UUID;

public interface UserPersistanceInput {
    void save(User user);

    void updatePassword(User user);

    void deleteByEmail(String email);
}
