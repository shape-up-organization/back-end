package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.UserMapper;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPersistenceAdapter implements UserPersistanceOutput {
    private UserRepositoryJpa userRepositoryJpa;

    private final UserMapper userMapper;

    public UserPersistenceAdapter(UserRepositoryJpa userRepositoryJpa, UserMapper userMapper) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        Boolean userExists = userRepositoryJpa.existsByEmail(user.getEmail().getValue());
        if (userExists) {
            throw new UserExistsByEmailException(user.getEmail().getValue());
        }

        UserEntity userEntity = userMapper.userToUserEntity(user);
        log.info("Starting process to save user on database: {}", userEntity.getId());
        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updatePassword(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException(user.getEmail().getValue());
        });

        userEntity.setPassword(user.getPassword().getValue());

        userRepositoryJpa.save(userEntity);
    }
}
