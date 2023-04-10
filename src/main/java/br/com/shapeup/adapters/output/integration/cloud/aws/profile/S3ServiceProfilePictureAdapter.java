package br.com.shapeup.adapters.output.integration.cloud.aws.profile;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.profile.ProfilePicture;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceProfilePictureAdapter implements S3ServiceProfilePictureGateway {
    private final AmazonS3 s3Client;
    private final UserJpaRepository UserJpaRepository;
    private final String bucketName = Dotenv.load().get("AWS_BUCKET_NAME");

    @Override
    public URI uploadProfileFilePicture(ProfilePicture profilePicture) {
        try {
            String fileName = profilePicture.getOriginalFilename();
            String contentType = profilePicture.getContentType();
            String uuid = profilePicture.getUuid();
            InputStream inputStream = profilePicture.getInputStream();

            return uploadProfileFilePicture(inputStream, fileName, contentType, uuid);
        } catch (IOException ex) {
            throw new RuntimeException("IO Error: " + ex.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public URI uploadProfileFilePicture(InputStream inputStream, String fileName, String contentType, String uuid) {
        UserEntity user = getUserByUuid(uuid);
        String newFileName = generateNewFileName(user, fileName);
        uploadToS3(inputStream, newFileName, contentType, uuid);

        return generateS3Url(newFileName);
    }

    @Override
    public URL getProfilePictureUrl(ProfilePicture profilePicture) {
        String fileName = profilePicture.getOriginalFilename();
        String uuid = profilePicture.getUuid();
        UserEntity username = UserJpaRepository.findById(UUID.fromString(uuid)).get();
        String newFileName = generateNewFileName(username, fileName);

        return s3Client.getUrl(bucketName, newFileName);
    }

    private UserEntity getUserByUuid(String uuid) {
        return UserJpaRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    private String generateNewFileName(UserEntity user, String fileName) {
        return "profile_picture/" + user.getUsername() + "--" + fileName;
    }

    @SneakyThrows
    private void uploadToS3(InputStream inputStream, String fileName, String contentType, String uuid) {
        var metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.addUserMetadata("USER_UUID", uuid);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
        validateFileSizeLimit(inputStream);
        metadata.setContentLength(inputStream.available());

        log.info("Uploading file to S3...");
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        log.info("File uploaded successfully.");
    }

    @SneakyThrows
    private URI generateS3Url(String fileName) {
        return s3Client.getUrl(bucketName, fileName).toURI();
    }

    private void validateFileSizeLimit(InputStream inputStream) throws IOException {
        int contentLength = inputStream.available();

        if (contentLength > 5 * 1024 * 1024) {
            throw new RuntimeException("File size is too big");
        }
    }
}