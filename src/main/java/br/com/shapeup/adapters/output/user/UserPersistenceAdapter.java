package br.com.shapeup.adapters.output.user;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.UserMapper;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements UserPersistanceOutput {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        userRepositoryJpa.save(userEntity);
    }
}
