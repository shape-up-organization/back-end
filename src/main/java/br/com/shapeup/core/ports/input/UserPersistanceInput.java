package br.com.shapeup.core.ports.input;

import br.com.shapeup.core.domain.user.User;
import java.net.URL;

public interface UserPersistanceInput {

    void deleteByEmail(String email);

    URL uploadPicture(Object file, String token);

    void updateUser(User user);
}
