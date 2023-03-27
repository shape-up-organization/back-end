package br.com.shapeup.core.ports.output.user;

import br.com.shapeup.core.domain.user.User;
import java.net.URL;

public interface UserPersistanceOutput {

    void updatePassword(User user);

    void deleteByEmail(String email);

    void updateName(User user);

    void updateLastName(User user);

    void updateCellPhone(User user);

    void updateBirth(User user);

    void updateBiography(User user);

    User findUserByUsername(String username);
}
