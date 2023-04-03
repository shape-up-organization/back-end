package br.com.shapeup.adapters.output.repository.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
@Component
public interface UserMapper {

    User userEntitytoUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);

    UserEntity userRegisterRequestToUserEntity(UserAuthRegisterRequest userAuthRegisterRequest);
}
