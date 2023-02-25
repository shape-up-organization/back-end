package br.com.shapeup.adapters.output.repository.mapper;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMappingImpl implements UserMapper{
    @Override
    public User userEntitytoUser(UserEntity userEntity) {
        return new User(
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getCellPhone(),
                userEntity.getPassword()
        );
    }

    @Override
    public UserEntity userToUserEntity(User user) {
        return new UserEntity(
                null,
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getCellPhone(),
                user.getPassword()
        );
    }
}
