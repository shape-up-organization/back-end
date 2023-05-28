package br.com.shapeup.adapters.output.integration.post.like;

import br.com.shapeup.adapters.output.integration.xp.XpAdapter;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostLikeEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.common.domain.enums.UserActionEnum;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.like.PostLikeOutput;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PostLikeAdapter implements PostLikeOutput {
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    @Override
    public void likePost(User user, String postId) {
       postLikeMongoRepository.save(
                    new PostLikeEntity(
                            UUID.randomUUID(),
                            user.getId().getValue(),
                            postId
                    ));
        UserEntity userEntity = userMapper.userToUserEntity(user);

        userEntity.setXp(UserActionEnum.LIKE.getXp());
        userJpaRepository.save(userEntity);
    }

    @Override
    public boolean postIsAlreadyLiked(User user, String postId) {
        return postLikeMongoRepository.existsByPostIdAndUserId(
                user.getId().getValue(),
                postId);
    }

    @Override
    public void unlikePost(User user, String postId) {
        postLikeMongoRepository.deleteByUserIdAndPostId(user.getId().getValue(), postId);

        UserEntity userEntity = userMapper.userToUserEntity(user);

        userEntity.setXp(userEntity.getXp() - 1);
        userJpaRepository.save(userEntity);
    }
}
