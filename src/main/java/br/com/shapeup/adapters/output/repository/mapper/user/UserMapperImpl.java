package br.com.shapeup.adapters.output.repository.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class UserMapperImpl implements UserMapper {

    @Override
    public User userEntitytoUser(UserEntity userEntity) {
        return User.newUser(
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                Email.create(userEntity.getEmail()),
                CellPhone.create(userEntity.getCellPhone()),
                Password.create(userEntity.getPassword()),
                Birth.create(userEntity.getBirth()),
                userEntity.getId()
        );
    }

    @Override
    public UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .id(UUID.fromString(user.getId().getValue()))
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail().getValue())
                .cellPhone(user.getCellPhone().getValue())
                .password(user.getPassword().getValue())
                .birth(user.getBirth().getValue())
                .build();
    }

    @Override
    public UserEntity userRegisterRequestToUserEntity(UserAuthRegisterRequest userAuthRegisterRequest) {
        return UserEntity.builder()
                .name(userAuthRegisterRequest.getName())
                .lastName(userAuthRegisterRequest.getLastName())
                .username(userAuthRegisterRequest.getUsername())
                .email(userAuthRegisterRequest.getEmail())
                .cellPhone(userAuthRegisterRequest.getCellPhone())
                .password(userAuthRegisterRequest.getPassword())
                .birth(LocalDate.parse(userAuthRegisterRequest.getBirth()))
                .build();
    }
}
