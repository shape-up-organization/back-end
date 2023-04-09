package br.com.shapeup.adapters.output.integration.profile;

import br.com.shapeup.adapters.output.integration.cloud.aws.profile.S3ServicePictureProfileGateway;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.profile.PictureProfile;
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
    private final S3ServicePictureProfileGateway s3Service;

    @Override
    public URL uploadPicture(Object file, String tokenJwt) {

        UserEntity user = validateUserExistsInDatabaseByEmailAndReturnSame(tokenJwt);

        PictureProfile pictureProfile = sendPictureToS3AndReturnSame((MultipartFile) file, user);

        return s3Service.getPictureProfileUrl(pictureProfile);
    }

    private PictureProfile sendPictureToS3AndReturnSame(MultipartFile file, UserEntity user) {

        PictureProfile pictureProfile = new PictureProfile(file, user.getId().toString());
        try {
            s3Service.uploadPictureProfileFile(pictureProfile);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return pictureProfile;
    }

    private UserEntity validateUserExistsInDatabaseByEmailAndReturnSame(String tokenJwt) {
        String userEmail = JwtService.extractEmailFromToken(tokenJwt);
        return userRepositoryJpa.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
    }
}
