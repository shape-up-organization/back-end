package br.com.shapeup.core.ports.output.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.core.domain.user.User;

public interface UserPersistanceOutput {

    void deleteByEmail(String email);

    URL uploadPicture(Object file, String token);

    User findUser(String email);

    void updateUser(String email, UserRequest userRequest);
}
