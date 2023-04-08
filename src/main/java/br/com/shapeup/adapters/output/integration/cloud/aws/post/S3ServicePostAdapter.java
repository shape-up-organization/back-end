package br.com.shapeup.adapters.output.integration.cloud.aws.post;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServicePostAdapter implements S3ServicePostGateway {
    private final AmazonS3 s3Client;

    private String bucketName = Dotenv.load().get("AWS_BUCKET_NAME");

    @Override
    public URI uploadPostPictureFile(MultipartFile file, UserEntity user) {
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            return uploadPostPictureFile(inputStream, fileName, contentType, user);

        } catch (IOException ex) {
            throw new RuntimeException("IO Error: " + ex.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public URI uploadPostPictureFile(InputStream inputStream, String fileName, String contentType, UserEntity user) {
        String newFileName = generateNewFileName(user, fileName);
        uploadToS3(inputStream, newFileName, contentType, user.getUsername());
        return generateS3Url(newFileName);
    }

    private String generateNewFileName(UserEntity user, String fileName) {
        return "posts/" + user.getUsername() + "--post--" + fileName;
    }

    @SneakyThrows
    private void uploadToS3(InputStream inputStream, String fileName, String contentType, String username) {
        var metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.addUserMetadata("USER_USERNAME", username);

        validateFileSizeLimit(inputStream);

        metadata.setContentLength(inputStream.available());

        log.info("Uploading post file to S3...");
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        log.info("File uploaded successfully.");
    }

    private static void validateFileSizeLimit(InputStream inputStream) throws IOException {
        int contentLength = inputStream.available();

        if (contentLength > 5 * 1024 * 1024) {
            throw new RuntimeException("File size is too big");
        }
    }

    @SneakyThrows
    private URI generateS3Url(String fileName) {
        return s3Client.getUrl(bucketName, fileName).toURI();
    }

    public URL getPostPictureUrl(MultipartFile file, String username) {
        String fileName = file.getOriginalFilename();
        String newFileName = username + "--post--" + fileName;
        return s3Client.getUrl(bucketName, newFileName);
    }
}
