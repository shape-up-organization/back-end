package br.com.shapeup.adapters.output.integration.cloud.aws.post;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface S3ServicePostGateway {
    URI uploadPostPictureFile(MultipartFile file, UserEntity user, UUID postId, int count) throws URISyntaxException;

    URI uploadPostPictureFile(InputStream inputStream, String fileName, String contentType, UserEntity user, UUID postId);

    URL getPostPictureUrl(MultipartFile file, String username, UUID postId, int count);

    void deletePostPhotos(List<String> photosUrls);
}
