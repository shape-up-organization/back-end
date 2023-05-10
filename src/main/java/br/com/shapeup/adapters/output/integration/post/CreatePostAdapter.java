package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.output.integration.cloud.aws.post.S3ServicePostGateway;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.CreatePostOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreatePostAdapter implements CreatePostOutput {
    private final UserMapper userMapper;
    private final S3ServicePostGateway s3Service;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;

    @Override
    public List<URL> createPost(Object[] files, User user, PostRequest request) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        List<URI> savingImages = Arrays.stream(files)
                .map(file -> sendPostPhotosToS3AndReturnSame((MultipartFile) file, userEntity))
                .toList();

        List<URL> postUrls = Arrays.stream(files)
                .map(file -> s3Service.getPostPictureUrl((MultipartFile) file, userEntity.getUsername()))
                .toList();

        PostEntity postEntity = new PostEntity(userEntity.getId(), request.getDescription());

        PostEntity savedPost  = postJpaRepository.save(postEntity);

        List<PostPhotoEntity> postPhotosEntitys = postUrls.stream()
                .map(url -> new PostPhotoEntity(url.toString(), savedPost.getId().toString()))
                .toList();

        postPhotoMongoRepository.saveAll(postPhotosEntitys);

        return postUrls;
    }

    private URI sendPostPhotosToS3AndReturnSame(MultipartFile file, UserEntity user) {
        try {
            URI urlPhoto = s3Service.uploadPostPictureFile(file, user);

            return urlPhoto;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createPostWithoutPhoto(User user, PostWithouPhotoRequest request) {
        UUID userId = UUID.fromString(user.getId().getValue());

        PostEntity postEntity = new PostEntity(userId, request.getDescription());

        postJpaRepository.save(postEntity);
    }
}
