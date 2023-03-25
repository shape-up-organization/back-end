package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceAdapter;
import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.PictureProfile;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
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
}
