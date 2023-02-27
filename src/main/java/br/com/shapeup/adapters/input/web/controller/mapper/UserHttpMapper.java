package br.com.shapeup.adapters.input.web.controller.mapper;

import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserHttpMapper {

    User toUser(UserRequest userRequest);
    User toUser(UserPasswordRequest userPasswordRequest);
}
