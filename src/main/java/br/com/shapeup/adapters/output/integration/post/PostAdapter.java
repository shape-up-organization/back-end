package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.output.integration.cloud.aws.post.S3ServicePostGateway;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PostAdapter implements PostOutput {
    private final UserJpaRepository userRepositoryJpa;
    private final S3ServicePostGateway s3Service;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;

    @Override
    public List<URL> createPost(MultipartFile[] files, String token, PostRequest request) {
        UserEntity user = validateUserExistsInDatabaseByEmailAndReturnSame(token);

        List<URI> savingImages = Arrays.stream(files)
                .map(file -> sendPostPhotosToS3AndReturnSame(file, user))
                .toList();

        List<URL> postUrls = Arrays.stream(files)
                .map(file -> s3Service.getPostPictureUrl(file, user.getUsername()))
                .toList();

        PostEntity postEntity = new PostEntity(user.getId(), request.getDescription());

        PostEntity savedPost  = postJpaRepository.save(postEntity);

        List<PostPhotoEntity> postPhotosEntitys = postUrls.stream()
                .map(url -> new PostPhotoEntity(url.toString(), savedPost.getId().toString()))
                .toList();

        postPhotoMongoRepository.saveAll(postPhotosEntitys);

        return postUrls;
    }

    private UserEntity validateUserExistsInDatabaseByEmailAndReturnSame(String tokenJwt) {
        String userEmail = JwtService.extractEmailFromToken(tokenJwt);
        return userRepositoryJpa.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
    }

    private URI sendPostPhotosToS3AndReturnSame(MultipartFile file, UserEntity user) {
        try {
            URI urlPhoto = s3Service.uploadPostPictureFile(file, user);

            return urlPhoto;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
