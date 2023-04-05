package br.com.shapeup.core.ports.input.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import java.net.URL;

public interface UserPersistanceInput {

    void deleteByEmail(String email);

    void updateUser(String email, UserRequest userRequest);
}
