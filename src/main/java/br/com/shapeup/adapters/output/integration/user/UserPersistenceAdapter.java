package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private UserRepositoryJpa userRepositoryJpa;

    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        String encodedPassword = passwordEncoder.encode(user.getPassword().getValue());
        userEntity.setPassword(encodedPassword);

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updateName(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        userEntity.setName(user.getName());

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updateLastName(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        userEntity.setLastName(user.getLastName());

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updateCellPhone(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        Boolean cellPhoneExists = userRepositoryJpa.existsByCellPhone(user.getCellPhone().getValue());

        if (cellPhoneExists) {
            throw new UserExistsByCellPhoneException(user.getCellPhone().getValue());
        }

        userEntity.setCellPhone(user.getCellPhone().getValue());

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updateBirth(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        userEntity.setBirth(user.getBirth().getValue());

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public void updateBiography(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        userEntity.setBiography(user.getBiography());

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
