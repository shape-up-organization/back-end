package br.com.shapeup.adapters.output.integration.auth;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.Role;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.domain.enums.UserActionEnum;
import br.com.shapeup.common.exceptions.auth.register.CellPhoneAlreadyExistsException;
import br.com.shapeup.common.exceptions.auth.register.UsernameInUseException;
import br.com.shapeup.common.exceptions.user.InvalidCredentialException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.common.exceptions.user.UserNotVerifiedException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.security.service.JwtService;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthAdapter implements AuthGateway {

    private final JwtService jwtService;
    private final UserJpaRepository UserJpaRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Map<String, Object> login(User user) {
        String email = user.getEmail().getValue();
        var userEntity = userJpaRepository.findByEmail(email).orElseThrow();
        validateUserDoesNotExistByEmail(email);

        validateUserIsActivated(userEntity);

        Authentication authentication = authenticateUser(email, userEntity.getOriginalPassword());

        return generateJwtToken(userEntity, authentication);
    }

    @Override
    public void register(UserAuthRegisterRequest userAuthRegisterRequest) {
        validateUserExistByEmailInDatabase(userAuthRegisterRequest);
        validateCellPhoneAlreadyExistsInDatabase(userAuthRegisterRequest.getCellPhone());

        UserEntity userEntity = mapUserAuthRegisterToUserEntityWithEncodedPassword(userAuthRegisterRequest);
        userEntity.setXp(UserActionEnum.CREATEACCOUNT.getXp());

        log.info("Starting process to save user on database: {}", userEntity.getUsername());
        UserJpaRepository.save(userEntity);
    }

    private UserEntity mapUserAuthRegisterToUserEntityWithEncodedPassword(UserAuthRegisterRequest userAuthRegisterRequest) {
        UserEntity userEntity = userMapper.userRegisterRequestToUserEntity(userAuthRegisterRequest);
        String encodedPassword = passwordEncoder.encode(userAuthRegisterRequest.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setOriginalPassword(userAuthRegisterRequest.getPassword());
        userEntity.setRoles(Set.of(new Role("ROLE_USER")));
        return userEntity;
    }

    private void validateUserExistByEmailInDatabase(UserAuthRegisterRequest userAuthRegisterRequest) {
        Boolean userExists = UserJpaRepository.existsByEmail(userAuthRegisterRequest.getEmail());

        if (userExists) {
            throw new InvalidCredentialException();
        }
    }

    private void validateCellPhoneAlreadyExistsInDatabase(String cellphone) {
        Boolean cellphoneAlreadyExistsInDatabase = UserJpaRepository.existsByCellPhoneContains(cellphone);

        if (cellphoneAlreadyExistsInDatabase) {
            throw new CellPhoneAlreadyExistsException();
        }
    }

    private void validateUserDoesNotExistByEmail(String email) {
        Boolean userExists = UserJpaRepository.existsByEmail(email);

        if (!userExists) {
            throw new UserNotFoundException(email);
        }
    }


    private Map<String, Object> generateJwtToken(
            UserEntity userEntity,
            Authentication authentication
    ) {

        if (authentication.isAuthenticated()) {
            String tokenGenerated = jwtService.generateToken(
                    userEntity.getId().toString(),
                    userEntity.getFullName(),
                    userEntity.getFullName(),
                    userEntity.getEmail(),
                    userEntity.getUsername(),
                    userEntity.getProfilePicture(),
                    userEntity.getXp().toString(),
                    userEntity.getBiography()
            );
            log.info("User {} authenticated", userEntity.getEmail());
            return Map.of("jwt-token", tokenGenerated);
        } else {
            throw new UserNotFoundException(userEntity.getEmail());
        }
    }

    private Authentication authenticateUser(String email, String password) {

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password)
        );
    }

    @Override
    public Boolean validateUserName(String username) {
        validateUserExistsByUserNameInDatabase(username);
        return true;
    }

    private void validateUserExistsByUserNameInDatabase(String username) {
        Boolean userNameIsAlreadyInUse = UserJpaRepository.existsByUsername(username);
        if (userNameIsAlreadyInUse) {
            throw new UsernameInUseException("Username is not available");
        }
    }

    private void validateUserIsActivated(UserEntity userEntity) {
        if (!userEntity.isActive()) {
            throw new UserNotVerifiedException("User is not verified");
        }
    }
}
