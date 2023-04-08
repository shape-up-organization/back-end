package br.com.shapeup.adapters.output.integration.auth;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthLoginRequest;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.Role;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.auth.register.CellPhoneAlreadyExistsException;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.security.service.JwtService;
import com.amazonaws.services.elasticache.model.UserAlreadyExistsException;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public Map<String, Object> login(UserAuthLoginRequest userAuthLoginRequest) {
        validateUserDoesNotExistByEmail(userAuthLoginRequest);

        Authentication authentication = authenticateUser(userAuthLoginRequest);

        return generateJwtToken(userAuthLoginRequest, authentication);
    }

    @Override
    public void register(UserAuthRegisterRequest userAuthRegisterRequest) {
        validateUserExistByEmailInDatabase(userAuthRegisterRequest);
        validateCellPhoneAlreadyExistsInDatabase(userAuthRegisterRequest.getCellPhone());

        UserEntity userEntity = mapUserAuthRegisterToUserEntityWithEncodedPassword(userAuthRegisterRequest);

        log.info("Starting process to save user on database: {}", userEntity.getUsername());
        UserJpaRepository.save(userEntity);
    }

    private UserEntity mapUserAuthRegisterToUserEntityWithEncodedPassword(UserAuthRegisterRequest userAuthRegisterRequest) {
        UserEntity userEntity = userMapper.userRegisterRequestToUserEntity(userAuthRegisterRequest);
        String encodedPassword = passwordEncoder.encode(userAuthRegisterRequest.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setRoles(Set.of(new Role("ROLE_USER")));
        return userEntity;
    }

    private void validateUserExistByEmailInDatabase(UserAuthRegisterRequest userAuthRegisterRequest) {
        Boolean userExists = UserJpaRepository.existsByEmail(userAuthRegisterRequest.getEmail());

        if (userExists) {
            throw new UserExistsByEmailException();
        }
    }

    private void validateCellPhoneAlreadyExistsInDatabase(String cellphone) {
        Boolean cellphoneAlreadyExistsInDatabase = UserJpaRepository.existsByCellPhoneContains(cellphone);

        if (cellphoneAlreadyExistsInDatabase) {
            throw new CellPhoneAlreadyExistsException();
        }
    }

    private void validateUserDoesNotExistByEmail(UserAuthLoginRequest userAuthLoginRequest) {
        Boolean userExists = UserJpaRepository.existsByEmail(userAuthLoginRequest.getEmail());

        if (!userExists) {
            throw new UserNotFoundException(userAuthLoginRequest.getEmail());
        }
    }


    private Map<String, Object> generateJwtToken(
            UserAuthLoginRequest userAuthLoginRequest,
            Authentication authentication
    ) {
        if (authentication.isAuthenticated()) {
            String tokenGenerated = jwtService.generateToken(
                    userAuthLoginRequest.getEmail(),
                    userAuthLoginRequest.getName(), userAuthLoginRequest.getId()
            );
            log.info("User {} authenticated", userAuthLoginRequest.getEmail());
            return Map.of("jwt-token", tokenGenerated);
        } else {
            throw new UserNotFoundException(userAuthLoginRequest.getEmail());
        }
    }

    private Authentication authenticateUser(UserAuthLoginRequest userAuthLoginRequest) {

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                userAuthLoginRequest.getEmail(),
                userAuthLoginRequest.getPassword())
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
            throw new UserAlreadyExistsException("User already exists");
        }
    }
}
