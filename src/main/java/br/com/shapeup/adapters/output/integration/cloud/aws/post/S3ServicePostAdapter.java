package br.com.shapeup.adapters.output.integration.cloud.aws.post;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import io.github.cdimascio.dotenv.Dotenv;
import io.vavr.control.Try;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServicePostAdapter implements S3ServicePostGateway {
    private final AmazonS3 s3Client;

    private final String bucketName = Dotenv.load().get("AWS_BUCKET_NAME");

    @Override
    public URI uploadPostPictureFile(MultipartFile file, UserEntity user, UUID postId, int count) {
        try {
            String fileName = file.getOriginalFilename() + count;
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            return uploadPostPictureFile(inputStream, fileName, contentType, user, postId);

        } catch (IOException ex) {
            throw new RuntimeException("IO Error: " + ex.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public URI uploadPostPictureFile(InputStream inputStream, String fileName, String contentType, UserEntity user, UUID postId) {
        String newFileName = generateNewFileName(user, fileName, postId);
        uploadToS3(inputStream, newFileName, contentType, user.getUsername());
        return generateS3Url(newFileName);
    }

    private String generateNewFileName(UserEntity user, String fileName, UUID postId) {
        return "posts/" + user.getUsername() + "--" + postId + "--post--" +
                fileName.replace(" ", "");
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

    public URL getPostPictureUrl(MultipartFile file, String username, UUID postId, int count) {
        String fileName = file.getOriginalFilename() + count;
        String newFileName = "posts/" + username + "--" + postId + "--post--" +
                fileName.replace(" ", "");

        return s3Client.getUrl(bucketName, newFileName);
    }

    @Override
    public void deletePostPhotos(List<String> photosUrls) {
        photosUrls.forEach(photoUrl -> Try.run(() -> listObjectsWithPrefix("posts/" + photoUrl)
                        .forEach(s3ObjectSummary -> s3Client.deleteObject(bucketName, s3ObjectSummary.getKey())))
                .onFailure(ex -> log.error("Error deleting photo url: " + photoUrl +" "+ ex.getMessage()))
                .onSuccess(aVoid -> log.info("Profile photo url successfully.")));
    }

    private List<S3ObjectSummary> listObjectsWithPrefix(String prefix) {
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        return result.getObjectSummaries();
    }
}
