package br.com.shapeup.adapters.output.repository.mapper;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userEntitytoUser(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    UserEntity userToUserEntity(User user);
}
