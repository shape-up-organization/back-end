package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.post.comment.PostCommentJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.common.domain.enums.UserActionEnum;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.common.utils.DateUtils;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.PostOutput;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostAdapter implements PostOutput {
    private final UserMapper userMapper;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final UserJpaRepository userJpaRepository;

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

        return createPostById(postEntity, currentUserEntity);
    }

    private List<PostResponse> getPostsByUser(UserEntity currentUser, UserEntity otherUser, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PostEntity> posts  =
                postJpaRepository.findPostEntitiesByUserEntityOrderByCreatedAtDesc(otherUser, pageRequest);

        return posts
                .stream()
                .map(postEntity -> createPostById(postEntity, currentUser))
                .toList();
    }

    private PostResponse createPostById(PostEntity postEntity, UserEntity currentUser) {
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

        currentUser.setXp(currentUser.getXp() + UserActionEnum.POST.getXp());
        userJpaRepository.save(currentUser);

        return new PostResponse(
                postEntity.getId().toString(),
                postEntity.getDescription(),
                DateUtils.formatDateTime(postEntity.getCreatedAt()),
                postLikeMongoRepository.countAllByPostId(postId),
                postCommentJpaRepository.countAllByIdPost(postId),
                photoUrls,
                isLiked,
                user.getUsername(),
                user.getProfilePicture(),
                user.getName(),
                user.getLastName(),
                user.getXp()
        );
    }

    @Override
    public List<PostResponse> getPostsFriends(User user, int page, int size) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        Page<PostEntity> posts =
                postJpaRepository.findPostFriends(userEntity.getId(), PageRequest.of(page, size));

        return posts
                .stream()
                .map(postEntity -> createPostById(postEntity, userEntity))
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
    public boolean existsPostByIdAndUser(User user, String postId) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        UUID postIdUUID = UUID.fromString(postId);

        return postJpaRepository.existsByUserEntityAndId(userEntity, postIdUUID);
    }

    @Override
    @Transactional
    public void deletePostById(User user, String postId) {
        UUID postIdUUID = UUID.fromString(postId);

        postCommentJpaRepository.deleteAllByIdPost(postId);
        postJpaRepository.deleteById(postIdUUID);
    }

    @Override
    public void createPostWithoutPhoto(User user, PostWithouPhotoRequest request) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        PostEntity postEntity = new PostEntity(userEntity, request.getDescription());

        postJpaRepository.save(postEntity);
    }

    @Override
    @Transactional
    public void deleteAllPostsByUserId(String userId) {
        postJpaRepository.deleteAllByUserEntityId(UUID.fromString(userId));
        postLikeMongoRepository.deleteAllByUserId(userId);
    }
}
