package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceAdapter;
import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.PictureProfile;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import br.com.shapeup.security.service.JwtService;
import jakarta.transaction.Transactional;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistanceOutput {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3ServiceAdapter s3Service;

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
    public URL uploadPicture(Object file, String tokenJwt) {

        String userEmail = JwtService.extractEmailFromToken(tokenJwt);
        UserEntity user = userRepositoryJpa.findByEmail(userEmail).orElseThrow(() -> {
            throw new UserNotFoundException(userEmail);
        });

        MultipartFile multipartFile = (MultipartFile) file;
        PictureProfile pictureProfile = new PictureProfile(multipartFile, user.getId().toString());
        s3Service.uploadFile(pictureProfile);

        var pictureUrl = s3Service.getPictureUrl(pictureProfile);

        return pictureUrl;
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
