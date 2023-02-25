package br.com.shapeup.adapters.input.web.controller.mapper;

import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserHttpMapperImpl implements UserHttpMapper {
    @Override
    public User toUser(UserRequest userRequest) {
        return new User(
                userRequest.getName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getCellPhone(),
                userRequest.getPassword()
        );
    }
}
