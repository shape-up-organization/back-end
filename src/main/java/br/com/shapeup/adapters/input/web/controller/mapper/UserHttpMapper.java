package br.com.shapeup.adapters.input.web.controller.mapper;

import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserHttpMapper {
    UserHttpMapper INSTANCE = Mappers.getMapper(UserHttpMapper.class);

    User toUser(UserRequest userRequest);
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    User toUser(UserPasswordRequest userPasswordRequest);
}
