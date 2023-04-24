package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.adapters.output.integration.cloud.aws.post.S3ServicePostGateway;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.adapters.output.repository.mongo.post.comment.PostCommentMongoRepository;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostAdapter implements PostOutput {
    private final UserJpaRepository userRepositoryJpa;
    private final S3ServicePostGateway s3Service;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final PostCommentMongoRepository postCommentMongoRepository;

    @Override
    public List<URL> createPost(Object[] files, String token, PostRequest request) {
        UserEntity user = validateUserExistsInDatabaseByEmailAndReturnSame(token);

        List<URI> savingImages = Arrays.stream(files)
                .map(file -> sendPostPhotosToS3AndReturnSame((MultipartFile) file, user))
                .toList();

        List<URL> postUrls = Arrays.stream(files)
                .map(file -> s3Service.getPostPictureUrl((MultipartFile) file, user.getUsername()))
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

    @Override
    public List<PostResponse> getPostsByUsername(String username, int page, int size) {
        UserEntity userEntity = userRepositoryJpa.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return getPostsByUser(userEntity, page, size);
    }

    @Override
    public PostResponse getPostById(String id) {
        return getPostById(UUID.fromString(id));
    }

    @Override
    public List<PostResponse> getUserPosts(String email, int page, int size) {
        UserEntity userEntity = userRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return getPostsByUser(userEntity, page, size);
    }

    private List<PostResponse> getPostsByUser(UserEntity user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostEntity> posts  = postJpaRepository.findPostEntitiesByUserEntityOrderByCreatedAtDesc(user, pageRequest);

        if (posts == null) {
            return null;
        }

        List<PostResponse> postsResponse = posts
                .stream()
                .map(post -> getPostById(post.getId()))
                .toList();

        return postsResponse;
    }

    private PostResponse getPostById(UUID id) {
        String idPost = id.toString();

        PostEntity postEntity = postJpaRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(idPost));

        List<PostPhotoEntity> urlPosts = postPhotoMongoRepository.findAllByIdPost(idPost);

        List<String> photoUrls = urlPosts
                .stream()
                .filter(postPhoto -> postPhoto.getIdPost().equals(idPost))
                .map(PostPhotoEntity::getPhotoUrl)
                .toList();

        PostResponse postResponse = new PostResponse(
                postEntity.getId().toString(),
                postEntity.getDescription(),
                formatDateTime(postEntity.getCreatedAt()),
                postLikeMongoRepository.countAllByPostId(idPost),
                postCommentMongoRepository.countAllByIdPost(idPost),
                photoUrls
        );

        return postResponse;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
