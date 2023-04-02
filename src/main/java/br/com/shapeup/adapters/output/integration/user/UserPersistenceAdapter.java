package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceAdapter;
import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.user.PictureProfile;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
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

    @Override
    public User findUser(String email) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });

        User user = userMapper.userEntitytoUser(userEntity);

        return user;
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(user.getEmail().getValue()).orElseThrow(() -> {
            throw new UserExistsByEmailException();
        });

        if(user.getCellPhone() != null)
            userEntity.setCellPhone(user.getCellPhone().getValue());

        if(user.getBirth() != null)
            userEntity.setBirth(user.getBirth().getValue());

        if(user.getBiography() != null)
            userEntity.setBiography(user.getBiography());

        if (user.getName() != null)
            userEntity.setName(user.getName());

        if (user.getLastName() != null)
            userEntity.setLastName(user.getLastName());

        if(user.getUsername() != null)
            userEntity.setUsername(user.getUsername());

        if (user.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword().getValue());
            userEntity.setPassword(encodedPassword);
        }

        userRepositoryJpa.save(userEntity);
    }
}
