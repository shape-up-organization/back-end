package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserMapper userMapper;
    private final S3ServicePostGateway s3Service;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final PostCommentMongoRepository postCommentMongoRepository;
    private final UserJpaRepository userJpaRepository;

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
    public List<PostResponse> getPostsByUsername(User currentUser, User otherUser, int page, int size) {
        UserEntity userEntity = userMapper.userToUserEntity(currentUser);
        UserEntity otherUserEntity = userMapper.userToUserEntity(otherUser);

        return getPostsByUser(userEntity, otherUserEntity, page, size);
    }

    @Override
    public PostResponse getPostById(User currentUser, String id) {
        PostEntity postEntity = postJpaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PostNotFoundException(id));

        UserEntity currentUserEntity = userMapper.userToUserEntity(currentUser);

        return mountPostById(postEntity, currentUserEntity);
    }

    private List<PostResponse> getPostsByUser(UserEntity currentUser, UserEntity otherUser, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PostEntity> posts  =
                postJpaRepository.findPostEntitiesByUserEntityOrderByCreatedAtDesc(otherUser, pageRequest);

        List<PostResponse> postsResponse = posts
                .stream()
                .map(postEntity -> mountPostById(postEntity, currentUser))
                .toList();

        return postsResponse;
    }

    private PostResponse mountPostById(PostEntity postEntity, UserEntity currentUser) {
        String postId = postEntity.getId().toString();

        List<PostPhotoEntity> urlPosts = postPhotoMongoRepository.findAllByIdPost(postId);

        List<String> photoUrls = urlPosts
                .stream()
                .filter(postPhoto -> postPhoto.getIdPost().equals(postId))
                .map(PostPhotoEntity::getPhotoUrl)
                .toList();

        UserEntity user = findUserById(postEntity.getUserEntity().getId());

        String userId = currentUser.getId().toString();

        boolean isLiked = postLikeMongoRepository.existsByPostIdAndUserId(postId, userId);

        PostResponse postResponse = new PostResponse(
                postEntity.getId().toString(),
                postEntity.getDescription(),
                DateUtils.formatDateTime(postEntity.getCreatedAt()),
                postLikeMongoRepository.countAllByPostId(postId),
                postCommentMongoRepository.countAllByIdPost(postId),
                photoUrls,
                isLiked,
                user.getUsername(),
                user.getProfilePicture(),
                user.getName(),
                user.getLastName(),
                user.getXp()
        );

        return postResponse;
    }

    @Override
    public List<PostResponse> getPostsFriends(User user, int page, int size) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PostEntity> posts =
                postJpaRepository.findPostFriends(userEntity.getId(), pageRequest);

        return posts
                .stream()
                .map(postEntity -> mountPostById(postEntity, userEntity))
                .toList();
    }

    @Override
    public boolean existsPostById(String postId) {
        UUID postIdUUID = UUID.fromString(postId);

        return postJpaRepository.existsById(postIdUUID);
    }

    private UserEntity findUserById(UUID id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Override
    public boolean existsPostByUsername(User user, int page, int size) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        return postJpaRepository.existsByUserEntity(userEntity);
    }

    @Override
    public void createPostWithoutPhoto(User user, PostWithouPhotoRequest request) {
        UUID userId = UUID.fromString(user.getId().getValue());

        PostEntity postEntity = new PostEntity(userId, request.getDescription());

        postJpaRepository.save(postEntity);
    }
}
