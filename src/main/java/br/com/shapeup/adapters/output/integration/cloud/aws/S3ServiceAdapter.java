package br.com.shapeup.adapters.output.integration.cloud.aws;

import br.com.shapeup.adapters.output.repository.jpa.user.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.model.user.PictureProfile;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceAdapter implements S3ServiceGateway {

    private final AmazonS3 s3Client;
    private final UserRepositoryJpa userRepositoryJpa;
    @Value("${s3.bucket}")
    private String bucketName;

    @Override
    public URI uploadFile(PictureProfile pictureProfile) {
        try {
            String fileName = pictureProfile.getOriginalFilename();
            String contentType = pictureProfile.getContentType();
            String uuid = pictureProfile.getUuid();
            InputStream inputStream = pictureProfile.getInputStream();
            return uploadFile(inputStream, fileName, contentType, uuid);
        } catch (IOException ex) {
            throw new RuntimeException("IO Error: " + ex.getMessage());
        }
    }

    @Override
    public URI uploadFile(InputStream inputStream, String fileName, String contentType, String uuid) {
        UserEntity user = getUserByUuid(uuid);
        String newFileName = generateNewFileName(user, fileName);
        uploadToS3(inputStream, newFileName, contentType, uuid);
        return generateS3Url(newFileName);
    }

    @Override
    public URL getPictureUrl(PictureProfile pictureProfile) {
        String fileName = pictureProfile.getOriginalFilename();
        String uuid = pictureProfile.getUuid();
        String username = userRepositoryJpa.findById(UUID.fromString(uuid)).get().getUsername();
        String newFileName = username + "--" + fileName;
        return s3Client.getUrl(bucketName, newFileName);
    }

    private UserEntity getUserByUuid(String uuid) {
        return userRepositoryJpa.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    private String generateNewFileName(UserEntity user, String fileName) {
        return user.getUsername() + "--" + fileName;
    }

    private void uploadToS3(InputStream inputStream, String fileName, String contentType, String uuid) {
        var metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.addUserMetadata("USER_UUID", uuid);
        log.info("Uploading file to S3...");
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        log.info("File uploaded successfully.");
    }

    @SneakyThrows
    private URI generateS3Url(String fileName) {
        return s3Client.getUrl(bucketName, fileName).toURI();
    }
}