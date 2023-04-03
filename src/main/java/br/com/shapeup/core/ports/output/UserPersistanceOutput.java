package br.com.shapeup.core.ports.output;

import br.com.shapeup.core.domain.user.User;
import java.net.URL;

public interface UserPersistanceOutput {

    void deleteByEmail(String email);

    URL uploadPicture(Object file, String token);

    User findUser(String email);

    void updateUser(User user);
}
