package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.UserMapper;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements UserPersistanceOutput {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private UserMapper userMapper;

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
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue());

        if (userEntity == null) {
            throw new UserExistsByEmailException(user.getEmail().getValue());
        }
        userEntity.setPassword(user.getPassword().getValue());

        userRepositoryJpa.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {

        if (!userRepositoryJpa.existsByEmail(email)) {
        throw new UserNotFoundException(email);
        }
            userRepositoryJpa.deleteByEmail(email);
    }
}