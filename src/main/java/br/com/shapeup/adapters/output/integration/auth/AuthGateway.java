package br.com.shapeup.adapters.output.integration.auth;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthLoginRequest;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import java.util.Map;

public interface AuthGateway {
    Map<String, Object> login(UserAuthLoginRequest userAuthLoginRequest);

    void register(UserAuthRegisterRequest userAuthRegisterRequest);

    Boolean validateUserName(String username);
}
