package br.com.shapeup.adapters.output.integration.user.profile;

import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceAdapter;
import br.com.shapeup.adapters.output.integration.cloud.aws.S3ServiceGateway;
import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.model.user.PictureProfile;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.ports.output.user.profile.ProfilePictureOutput;
import br.com.shapeup.security.service.JwtService;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfilePictureAdapter implements ProfilePictureOutput {
    private final UserRepositoryJpa userRepositoryJpa;
    private final S3ServiceGateway s3Service;

    @Override
    public URL uploadPicture(Object file, String tokenJwt) {

        UserEntity user = validateUserExistsInDatabaseByEmailAndReturnSame(tokenJwt);

        PictureProfile pictureProfile = sendPictureToS3AndReturnSame((MultipartFile) file, user);

        return s3Service.getPictureUrl(pictureProfile);
    }

    private PictureProfile sendPictureToS3AndReturnSame(MultipartFile file, UserEntity user) {

        PictureProfile pictureProfile = new PictureProfile(file, user.getId().toString());
        try {
            s3Service.uploadFile(pictureProfile);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return pictureProfile;
    }

    private UserEntity validateUserExistsInDatabaseByEmailAndReturnSame(String tokenJwt) {
        String userEmail = JwtService.extractEmailFromToken(tokenJwt);
        UserEntity user = userRepositoryJpa.findByEmail(userEmail).orElseThrow(() -> {
            throw new UserNotFoundException(userEmail);
        });
        return user;
    }
}
