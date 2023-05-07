package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.adapters.output.integration.cloud.aws.post.S3ServicePostGateway;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.adapters.output.repository.mongo.post.comment.PostCommentMongoRepository;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.common.utils.DateUtils;
import br.com.shapeup.core.domain.user.User;
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
    private final UserMapper userMapper;

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
    public List<PostResponse> getPostsByUsername(User user, User otherUser, int page, int size) {
        return getPostsByUser(otherUser, page, size, user);
    }

    @Override
    public PostResponse getPostById(User user, String postId) {
        return getPostById(UUID.fromString(postId), user, null);
    }

    @Override
    public List<PostResponse> getUserPosts(User user, int page, int size) {
        return getPostsByUser(user, page, size, null);
    }

    private List<PostResponse> getPostsByUser(User user, int page, int size, User otherUser) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        UserEntity userEntity = userMapper.userToUserEntity(user);

        Page<PostEntity> posts  = postJpaRepository.findPostEntitiesByUserEntityOrderByCreatedAtDesc(userEntity, pageRequest);

        if (posts == null) {
            return null;
        }

        List<PostResponse> postsResponse = posts
                .stream()
                .map(post -> getPostById(post.getId(), user, otherUser))
                .toList();

        return postsResponse;
    }

    private PostResponse getPostById(UUID id, User user, User otherUser) {
        String idPost = id.toString();

        PostEntity postEntity = postJpaRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(idPost));

        List<PostPhotoEntity> urlPosts = postPhotoMongoRepository.findAllByIdPost(idPost);

        List<String> photoUrls = urlPosts
                .stream()
                .filter(postPhoto -> postPhoto.getIdPost().equals(idPost))
                .map(PostPhotoEntity::getPhotoUrl)
                .toList();

        boolean isLiked = whoLiked(user, otherUser);

        PostResponse postResponse = new PostResponse(
                postEntity.getId().toString(),
                postEntity.getDescription(),
                DateUtils.formatDateTime(postEntity.getCreatedAt()),
                postLikeMongoRepository.countAllByPostId(idPost),
                postCommentMongoRepository.countAllByIdPost(idPost),
                photoUrls,
                isLiked
        );

        return postResponse;
    }

    private boolean whoLiked(User user, User otherUser) {
        if(otherUser == null) {
            return postLikeMongoRepository.existsByUserId(user.getId().getValue());
        }
        return postLikeMongoRepository.existsByUserId(otherUser.getId().getValue());
    }
}
