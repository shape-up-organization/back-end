package br.com.shapeup.core.ports.output;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.core.domain.user.User;
import java.net.URL;

public interface UserPersistanceOutput {

    void deleteByEmail(String email);

    User findUser(String email);

    void updateUser(String email, UserRequest userRequest);
}
