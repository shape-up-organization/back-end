package br.com.shapeup.adapters.output.integration.cloud.aws.post;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public interface S3ServicePostGateway {
    URI uploadPostPictureFile(MultipartFile file, UserEntity user) throws URISyntaxException;
    URI uploadPostPictureFile(InputStream inputStream, String fileName, String contentType, UserEntity user);
    URL getPostPictureUrl(MultipartFile file, String username);
}
