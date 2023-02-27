package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.UserMapper;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements UserPersistanceOutput {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        log.info("Starting process to save user on database: {}", userEntity.getId());
        userRepositoryJpa.save(userEntity);
    }
}
