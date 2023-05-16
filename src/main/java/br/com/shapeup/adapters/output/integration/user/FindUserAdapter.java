package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class FindUserAdapter implements FindUserOutput {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        return userMapper.userEntitytoUser(userEntity);
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userJpaRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return userMapper.userEntitytoUser(userEntity);
    }

    public User findById(UUID id) {
        UserEntity userEntity = userJpaRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));

        return userMapper.userEntitytoUser(userEntity);
    }
}
