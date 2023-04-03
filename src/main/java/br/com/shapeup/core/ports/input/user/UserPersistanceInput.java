package br.com.shapeup.core.ports.input.user;

import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import java.net.URL;

public interface UserPersistanceInput {

    void deleteByEmail(String email);

    URL uploadPicture(Object file, String token);

    void updateUser(String email, UserRequest userRequest);
}
