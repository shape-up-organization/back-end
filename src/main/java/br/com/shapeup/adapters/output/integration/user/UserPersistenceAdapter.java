package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceAdapter;
import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private final UserRepositoryJpa userRepositoryJpa;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        userEntity.setActive(false);

        userRepositoryJpa.save(userEntity);
    }

    @Override
    public User findUser(String email) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        User user = userMapper.userEntitytoUser(userEntity);

        return user;
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(email).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        if(userRequest.getCellPhone() != null) {
            CellPhone.validateCellPhone(userRequest.getCellPhone());
            userEntity.setCellPhone(userRequest.getCellPhone());
        }

        if(userRequest.getBirth() != null) {
            var birth = Birth.convertBirth(userRequest.getBirth());

            Birth.validateBirth(birth);
            userEntity.setBirth(birth);
        }

        if(userRequest.getBiography() != null) {
            userEntity.setBiography(userRequest.getBiography());
        }

        if(userRequest.getName() != null) {
            userEntity.setName(userRequest.getName());
        }

        if(userRequest.getLastName() != null) {
            userEntity.setLastName(userRequest.getLastName());
        }

        if(userRequest.getUsername() != null) {
            userEntity.setUsername(userRequest.getUsername());
        }

        if(userRequest.getPassword() != null) {
            Password.validatePassword(userRequest.getPassword());

            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
            userEntity.setPassword(encodedPassword);
        }

        userRepositoryJpa.save(userEntity);
    }
}
