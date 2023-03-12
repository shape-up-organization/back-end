package br.com.shapeup.core.ports.output;

import br.com.shapeup.core.domain.user.User;

public interface UserPersistanceOutput {
    void save(User user);

    void updatePassword(User user);

    void updateName(User user);

    void updateLastName(User user);

    void updateCellPhone(User user);

    void updateBirth(User user);

    void updateBiography(User user);
}
