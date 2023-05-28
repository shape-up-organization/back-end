package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.output.integration.cloud.aws.post.S3ServicePostGateway;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.common.utils.QueueObj;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.PostS3Output;
import jakarta.transaction.Transactional;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PostS3Adapter implements PostS3Output {
    private final UserMapper userMapper;
    private final S3ServicePostGateway s3Service;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;

    @Override
    public List<URL> createPost(Object[] files, User user, PostRequest request) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        UUID postId = UUID.randomUUID();

        savingImages(files, userEntity, postId);

        List<URL> postUrls = getUrls(files, userEntity, postId);

        PostEntity postEntity = new PostEntity(postId, userEntity, request.getDescription());

        PostEntity savedPost  = postJpaRepository.save(postEntity);

        List<PostPhotoEntity> postPhotosEntitys = postUrls.stream()
                .map(url -> new PostPhotoEntity(url.toString(), savedPost.getId().toString()))
                .toList();

        postPhotoMongoRepository.saveAll(postPhotosEntitys);

        return postUrls;
    }

    private List<URL> getUrls(Object[] files, UserEntity userEntity, UUID postId) {
        List<URL> postUrls = new ArrayList<>();

        for(int i = 0 ; i < files.length; i++) {
            postUrls.add(s3Service.getPostPictureUrl(
                    (MultipartFile) files[i], userEntity.getUsername(), postId, i));
        }

        return postUrls;
    }

    private List<URL> getUrlsAsync(QueueObj<Object> files, UserEntity userEntity, UUID postId) {
        List<URL> postUrls = new ArrayList<>();

        for(int i = 0 ; i < files.getSize(); i++) {
            postUrls.add(s3Service.getPostPictureUrl(
                    (MultipartFile) files.poll(), userEntity.getUsername(), postId, i));
        }
        return postUrls;
    }

    private void savingImages(Object[] files, UserEntity userEntity, UUID postId) {
        for (int i = 0; i < files.length; i++) {
            sendPostPhotosToS3AndReturnSame((MultipartFile) files[i], userEntity, postId, i);
        }
    }

    private void sendPostPhotosToS3AndReturnSame(MultipartFile file, UserEntity user, UUID postId, int count) {
        try {
            s3Service.uploadPostPictureFile(file, user, postId, count);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletePostPhotos(User user, String postId) {
        List<PostPhotoEntity> urlPosts = postPhotoMongoRepository.findAllByIdPost(postId);

        List<String> photoUrls = urlPosts
                .stream()
                .map(PostPhotoEntity::getPhotoUrl)
                .toList();

        postPhotoMongoRepository.deleteAllByIdPost(postId);

        photoUrls = splitPhostUrl(photoUrls);

        s3Service.deletePostPhotos(photoUrls);
    }

    private List<String> splitPhostUrl(List<String> photoUrls) {
        return photoUrls.stream()
                .map(url -> url.split("/posts/")[1])
                .toList();
    }

    @Override
    public void createPostAsync(QueueObj<Object> files, User user, PostRequest request) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        UUID postId = UUID.randomUUID();

        CompletableFuture.runAsync(() -> {
            savingImagesAsync(files, userEntity, postId);

            List<URL> postUrls = getUrlsAsync(files, userEntity, postId);

            PostEntity postEntity =
                    new PostEntity(postId, userEntity, request.getDescription());

            postJpaRepository.save(postEntity);

            List<PostPhotoEntity> postPhotosEntitys = postUrls.stream()
                    .map(url -> new PostPhotoEntity(url.toString(), postId.toString()))
                    .toList();

            postPhotoMongoRepository.saveAll(postPhotosEntitys);
        });
    }

    private void savingImagesAsync(QueueObj<Object> files, UserEntity userEntity, UUID postId) {
        for (int i = 0; i < files.getSize(); i++) {
            sendPostPhotosToS3AndReturnSameAsync((MultipartFile) files.poll(), userEntity, postId, i);
        }
    }

    private void sendPostPhotosToS3AndReturnSameAsync(MultipartFile file, UserEntity user, UUID postId, int count) {
        try {
            s3Service.uploadPostPictureFile(file, user, postId, count);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletePostPhotosByUserId(String userId) {
        List<PostEntity> posts = postJpaRepository.findAllByUserEntityId(UUID.fromString(userId));

        List<String> photoUrls = posts.stream()
                .map(post -> postPhotoMongoRepository.findAllByIdPost(post.getId().toString()))
                .flatMap(List::stream)
                .map(PostPhotoEntity::getPhotoUrl)
                .toList();

        posts.forEach(post -> postPhotoMongoRepository.deleteAllByIdPost(post.getId().toString()));

        photoUrls = splitPhostUrl(photoUrls);

        s3Service.deletePostPhotos(photoUrls);
    }
}
