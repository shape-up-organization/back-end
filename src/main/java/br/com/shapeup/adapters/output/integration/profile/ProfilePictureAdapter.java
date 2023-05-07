package br.com.shapeup.adapters.output.integration.profile;

import br.com.shapeup.adapters.output.integration.cloud.aws.profile.S3ServiceProfilePictureGateway;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.profile.ProfilePicture;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.ports.output.profile.ProfilePictureOutput;
import br.com.shapeup.security.service.JwtService;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfilePictureAdapter implements ProfilePictureOutput {

    private final UserJpaRepository userRepositoryJpa;
    private final S3ServiceProfilePictureGateway s3Service;

    @Override
    public URL uploadPicture(Object file, String tokenJwt) {

        UserEntity user = validateUserExistsInDatabaseByEmailAndReturnSame(tokenJwt);
        ProfilePicture profilePicture = sendPictureToS3AndReturnSame((MultipartFile) file, user);
        URL url = s3Service.getProfilePictureUrl(profilePicture);

        user.setProfilePicture(url.toString());
        userRepositoryJpa.save(user);

        return url;
    }

    @Override
    public Boolean haveProfilePictureInBucket(String username) {
        return s3Service.haveProfilePictureInBucket(username);
    }

    @Override
    public void deletePicture(String username) {
        s3Service.deletePicture(username);
    }

    private ProfilePicture sendPictureToS3AndReturnSame(MultipartFile file, UserEntity user) {

        ProfilePicture profilePicture = new ProfilePicture(file, user.getId().toString());
        try {
            s3Service.uploadProfileFilePicture(profilePicture);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return profilePicture;
    }

    private UserEntity validateUserExistsInDatabaseByEmailAndReturnSame(String tokenJwt) {
        String userEmail = JwtService.extractEmailFromToken(tokenJwt);
        return userRepositoryJpa.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
    }
}
